package com.example.managementsystem.Service;


import com.example.managementsystem.DAO.AssessBatchDAO;
import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.DAO.OrganizationDAO;
import com.example.managementsystem.DAO.UserDAO;
import com.example.managementsystem.Model.AssessBatchPO;
import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.OrganizationPO;
import com.example.managementsystem.Model.UserPO;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;

@Service
public class AssessGroupService {

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private AssessBatchDAO assessBatchDAO;

    public ResultInfo createAssessGroup(Map<String, Object>assessGroupInfo){
        AssessGroupPO assessGroup = new AssessGroupPO();
        assessGroup.setCreateTime(new Date(System.currentTimeMillis()));
        assessGroup.setName((String)assessGroupInfo.get("name"));
        List<Map<String, Object>> members = (List<Map<String, Object>>) assessGroupInfo.get("member");
        String memberStr = "";
        String adminStr = "";
        for(int i = 0;i < members.size(); ++i){
            Map<String, Object> member = members.get(i);
            if(i == 0){
                memberStr = memberStr + member.get("user_id");
            } else {
                memberStr = memberStr + "," + member.get("user_id");
            }
        }
        assessGroup.setAdminList(adminStr);
        assessGroup.setMemberList(memberStr);
        assessGroup.setSuperior((int)assessGroupInfo.get("superior"));
        int assessGroupId = assessGroupDAO.updateAssessGroup(assessGroup);

        // 这里需要更新 user 的 adminAssessGroup 字段
        String[] adminIdList = adminStr.split(",");
        List<Integer> adminIdLists = new ArrayList<>();
        for(int i = 0;i < adminIdList.length; ++i){
            UserPO user = userDAO.getUserById(parseInt(adminIdList[i])).get(0);
            String[] oldAdminAssessGroup = user.getAdminAssessGroup().split(",");
            for(int j = 0;j < oldAdminAssessGroup.length; ++j){
                adminIdLists.add(parseInt(oldAdminAssessGroup[j]));
            }
            adminIdLists.add(assessGroupId);
            adminIdLists = adminIdLists.stream().distinct().collect(Collectors.toList());
            String newAdminIdStr = "";
            for(int j = 0;j < adminIdLists.size(); ++j){
                if(j == 0)newAdminIdStr = newAdminIdStr + adminIdLists.get(j);
                else newAdminIdStr = newAdminIdStr + "," + adminIdLists.get(j);
            }
            user.setAdminAssessGroup(newAdminIdStr);
            userDAO.updateUser(user);
        }

        // 更新上层Organization 的 subGroupIdList 字段
        OrganizationPO organization = organizationDAO.getOrganizationById((int)assessGroupInfo.get("superior")).get(0);
        String subGroupIdList = organization.getSubGroupIdList();
        if(subGroupIdList.length() == 0){
            subGroupIdList += assessGroupId;
        } else {
            subGroupIdList += "," + assessGroupId;
        }
        organization.setSubGroupIdList(subGroupIdList);
        organizationDAO.updateOrganization(organization);

        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo updateAssessGroup(Map<String, Object>assessGroupInfo){
        AssessGroupPO assessGroup = assessGroupDAO.getGroupById((int)assessGroupInfo.get("group_id")).get(0);
        assessGroup.setName((String)assessGroupInfo.get("name"));
        List<Map<String, Object>> members = (List<Map<String, Object>>) assessGroupInfo.get("member");
        String memberStr = "";
        String adminStr = "";
        for(int i = 0;i < members.size(); ++i){
            Map<String, Object> member = members.get(i);
            if(i == 0){
                memberStr = memberStr + member.get("user_id");
            } else {
                memberStr = memberStr + "," + member.get("user_id");
            }
            if((int)member.get("is_admin") == 1){
                if(adminStr.equals("")){
                    adminStr = adminStr + member.get("user_id");
                } else {
                    adminStr = adminStr + "," + member.get("user_id");
                }
            }
        }
        // 更新User AdminAssessGroup 字段
        List<String> adminIdList = Arrays.asList(adminStr.split(","));
        List<String> oldAdminIdList = Arrays.asList(assessGroup.getAdminList().split(","));
        for(int i = 0;i < oldAdminIdList.size(); ++i){
            if(!adminIdList.contains(oldAdminIdList.get(i))){
                UserPO user = userDAO.getUserById(parseInt(oldAdminIdList.get(i))).get(0);
                List<String> userAdminAssessGroupIdList = Arrays.asList(user.getAdminAssessGroup().split(","));
                List<String> newUserAdminAssessGroupIdList = new ArrayList<>();
                for(int j = 0;j < userAdminAssessGroupIdList.size(); ++j){
                    if(!userAdminAssessGroupIdList.get(j).equals(String.valueOf(assessGroup.getGroup_id()))){
                        newUserAdminAssessGroupIdList.add(userAdminAssessGroupIdList.get(j));
                    }
                }
                String newUserAdminAssessGroupIdStr = "";
                for(int j = 0;j < newUserAdminAssessGroupIdList.size(); ++j){
                    if(j == 0) newUserAdminAssessGroupIdStr = newUserAdminAssessGroupIdStr + newUserAdminAssessGroupIdList.get(j);
                    else newUserAdminAssessGroupIdStr = newUserAdminAssessGroupIdStr + "," + newUserAdminAssessGroupIdList.get(j);
                }
                user.setAdminAssessGroup(newUserAdminAssessGroupIdStr);
                userDAO.updateUser(user);
            }
        }

        assessGroup.setAdminList(adminStr);
        assessGroup.setMemberList(memberStr);
        assessGroupDAO.updateAssessGroup(assessGroup);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo deleteAssessGroupById(int groupId){

        // 消除User中的记录
        AssessGroupPO group = assessGroupDAO.getGroupById(groupId).get(0);
        String[] adminUserIdStr = group.getAdminList().split(",");
        for(int i = 0;i < adminUserIdStr.length; ++i){
            UserPO user = userDAO.getUserById(parseInt(adminUserIdStr[i])).get(0);
            String[] userAdminAssessGroupIdList = user.getAdminAssessGroup().split(",");
            List<String> newUserAdminAssessGroupIdList = new ArrayList<>();
            for(int j = 0;j < userAdminAssessGroupIdList.length; ++j){
                if(parseInt(userAdminAssessGroupIdList[j]) != groupId){
                    newUserAdminAssessGroupIdList.add(userAdminAssessGroupIdList[j]);
                }
            }
            String newUserAdminAssessGroupIdStr = "";
            for(int j = 0;j < newUserAdminAssessGroupIdList.size(); ++j){
                if(j == 0)newUserAdminAssessGroupIdStr = newUserAdminAssessGroupIdStr + newUserAdminAssessGroupIdList.get(j);
                else newUserAdminAssessGroupIdStr = newUserAdminAssessGroupIdStr + "," + newUserAdminAssessGroupIdList.get(j);
            }
            user.setAdminAssessGroup(newUserAdminAssessGroupIdStr);
            userDAO.updateUser(user);
        }

        // 清除上层Organization中的记录
        OrganizationPO organization = organizationDAO.getOrganizationById(group.getSuperior()).get(0);
        String[] subGroupIdList = organization.getSubGroupIdList().split(",");
        List<String> newSubGroupIdList = new ArrayList<>();
        for(int i = 0;i < subGroupIdList.length;++i){
            if(parseInt(subGroupIdList[i]) != groupId){
                newSubGroupIdList.add(subGroupIdList[i]);
            }
        }
        String newSubGroupIdStr = "";
        for(int i = 0;i < newSubGroupIdList.size(); ++i){
            if(i == 0)newSubGroupIdStr += newSubGroupIdList.get(i);
            else newSubGroupIdStr += "," + newSubGroupIdList.get(i);
        }
        organization.setSubGroupIdList(newSubGroupIdStr);
        organizationDAO.updateOrganization(organization);

        // 清除该assess group 关联到的 assess batch
        List<AssessBatchPO> assessBatches = assessBatchDAO.getTotalAssessBatchByGroupId(groupId);
        for(int i = 0;i < assessBatches.size(); ++i){
            assessBatchDAO.deleteAssessBatchById(assessBatches.get(i).getAssess_batch_id());
        }

        // 删除本身
        assessGroupDAO.deleteAssessGroupById(groupId);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getAssessGroupByOrganizationId(int organizationId, int page, int limit){
        OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
        String[] subAssessGroupList = organization.getSubGroupIdList().split(",");
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(subAssessGroupList.length, page * limit); ++i){
            if(subAssessGroupList[i].equals(""))continue;
            Map<String, Object> temp = new HashMap<>();
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(parseInt(subAssessGroupList[i])).get(0);
            temp.put("index", ++index);
            temp.put("assess_group_id", assessGroup.getGroup_id());
            temp.put("name", assessGroup.getName());
            String[] members = assessGroup.getMemberList().split(",");
            if(assessGroup.getMemberList().equals("")){
                temp.put("number", 0);
            } else {
                temp.put("number", members.length);
            }
            tempList.add(temp);
        }
        result.put("count", subAssessGroupList.length);
        result.put("result", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo newAssessGroup(String groupName, int superiorId){
        OrganizationPO organization = organizationDAO.getOrganizationById(superiorId).get(0);
        AssessGroupPO assessGroup = new AssessGroupPO();
        assessGroup.setMemberList("");
        assessGroup.setAdminList("");
        assessGroup.setSuperior(superiorId);
        assessGroup.setName(groupName);
        assessGroup.setCreateTime(new Date());
        int assessGroupId = assessGroupDAO.updateAssessGroup(assessGroup);
        String subAssessGroupIdStr = organization.getSubGroupIdList();
        if(subAssessGroupIdStr.equals("")){
            subAssessGroupIdStr = subAssessGroupIdStr + assessGroupId;
        } else {
            subAssessGroupIdStr = subAssessGroupIdStr + "," + assessGroupId;
        }
        organization.setSubGroupIdList(subAssessGroupIdStr);
        organizationDAO.updateOrganization(organization);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getTotalAssessGroup(){
        List<AssessGroupPO> assessGroups = assessGroupDAO.getTotalAssessGroup();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        for(int i = 0;i < assessGroups.size(); ++i){
            AssessGroupPO assessGroup = assessGroups.get(i);
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", assessGroup.getName());
            temp.put("assess_group_id", assessGroup.getGroup_id());
            tempList.add(temp);
        }
        result.put("result", tempList);
        return new ResultInfo(200, "success", result);
    }
}
