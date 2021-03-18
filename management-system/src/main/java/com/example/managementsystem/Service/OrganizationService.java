package com.example.managementsystem.Service;

import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.DAO.OrganizationDAO;
import com.example.managementsystem.DAO.UserDAO;
import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.OrganizationPO;
import com.example.managementsystem.Model.UserPO;
import com.example.managementsystem.util.ResultInfo;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;


@Service
public class OrganizationService {
    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    @Autowired
    private AssessGroupService assessGroupService;

    public ResultInfo getTotalOrganization(){
        List<OrganizationPO> organizations = organizationDAO.getTotalOrganization();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        for(int i = 0;i < organizations.size(); ++i){
            OrganizationPO organization = organizations.get(i);
            if(organization.getOrganization_id() == 1)continue;
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", organization.getName());
            temp.put("organization_id", organization.getOrganization_id());
            tempList.add(temp);
        }
        result.put("organizations", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo updateOrganizationManageArea(int organizationId, String manageOrganizationArea, String manageAssessGroupArea){
        OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
        organization.setManageOrganizationIdList(manageOrganizationArea);
        organization.setManageAssessGroupIdList(manageAssessGroupArea);
        organizationDAO.updateOrganization(organization);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo createOrganization(Map<String, Object> organizationInfo){
        OrganizationPO organization = new OrganizationPO();
        organization.setName((String)organizationInfo.get("name"));
        organization.setManageAssessGroupIdList("");
        organization.setManageOrganizationIdList("");
        organization.setAdminIdList("");
        organization.setSuperior((int)organizationInfo.get("superior"));
        int organizationId = organizationDAO.updateOrganization(organization);
        OrganizationPO superior = organizationDAO.getOrganizationById((int)organizationInfo.get("superior")).get(0);
        String subOrganizationStr = superior.getSubOrganizationIdList();
        if(subOrganizationStr.equals("")){
            subOrganizationStr = subOrganizationStr + organizationId;
        } else {
            subOrganizationStr = subOrganizationStr + "," + organizationId;
        }
        superior.setSubOrganizationIdList(subOrganizationStr);
        organizationDAO.updateOrganization(superior);

        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getOrganizationTreeByUserId(int userId){
        UserPO user = userDAO.getUserById(userId).get(0);
        String[] adminOrganizationStr = user.getAdminOrganization().split(",");
        String[] adminAssessGroupStr = user.getAdminAssessGroup().split(",");
        List<Integer> organizationNode = organizationDAO.getHighestOrganizationNode(1, adminOrganizationStr);
        List<Integer> assessGroupNode = organizationDAO.getHighestAssessGroupNode(1, adminOrganizationStr, adminAssessGroupStr);
        List<Map<String, Object>> result = new ArrayList<>();
        for(int i = 0;i < organizationNode.size(); ++i){
            result.add(organizationDAO.constructTreeNode(organizationNode.get(i)));
        }
        for(int i = 0;i < assessGroupNode.size(); ++i){
            Map<String, Object> temp = new HashMap<>();
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(assessGroupNode.get(i)).get(0);
            temp.put("title", assessGroup.getName());
            temp.put("id", assessGroup.getGroup_id());
            temp.put("node_type", "assess_group");
            result.add(temp);
        }
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo setOrganizationAdminUser(List<Map<String, Object>> userInfo, int organizationId, int isOrganization){
        // 处理 user 的管理范围
        for(int i = 0;i < userInfo.size(); ++i){
            Map<String, Object> user = userInfo.get(i);
            UserPO userPO = userDAO.getUserById((int)user.get("user_id")).get(0);
            if(isOrganization == 1){
                String[] adminOrganization;
                if(userPO.getAdminOrganization() != null){
                    adminOrganization = userPO.getAdminOrganization().split(",");
                } else {
                    adminOrganization = new String[0];
                }

                int flag = 0;
                for(int j = 0 ;j < adminOrganization.length; ++j){
                    if(String.valueOf(organizationId).equals(adminOrganization[j])){
                        flag = 1;
                        break;
                    }
                }
                if(flag != 1){
                    String oldAdminOrganization = userPO.getAdminOrganization();
                    String newAdminOrganization;
                    if(oldAdminOrganization.equals("")){
                        newAdminOrganization = oldAdminOrganization + organizationId;
                    } else  {
                        newAdminOrganization = oldAdminOrganization + "," + organizationId;
                    }
                    userPO.setAdminOrganization(newAdminOrganization);
                    userDAO.updateUser(userPO);
                }
            } else {
                String[] adminAssessGroup;
                if(userPO.getAdminAssessGroup() != null){
                    adminAssessGroup = userPO.getAdminAssessGroup().split(",");
                } else {
                    adminAssessGroup = new String[0];
                }
                int flag = 0;
                for (int j = 0; j < adminAssessGroup.length; ++j) {
                    if (String.valueOf(organizationId).equals(adminAssessGroup[j])) {
                        flag = 1;
                        break;
                    }
                }
                if (flag != 1) {
                    String oldAdminAssessGroup = userPO.getAdminAssessGroup();
                    String newAdminAssessGroup;
                    if (oldAdminAssessGroup.equals("")) {
                        newAdminAssessGroup = oldAdminAssessGroup + organizationId;
                    } else {
                        newAdminAssessGroup = oldAdminAssessGroup + "," + organizationId;
                    }
                    userPO.setAdminAssessGroup(newAdminAssessGroup);
                    userDAO.updateUser(userPO);
                }
            }
        }

        // 处理Organization 的管理员标签

        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            String[] organizationAdmin = organization.getAdminIdList().split(",");
            String organizationAdminStr = organization.getAdminIdList();
            for(int i = 0;i < userInfo.size(); ++i){
                Map<String, Object> user = userInfo.get(i);
                int userId = (int)user.get("user_id");
                int flag = 1;
                for(int j = 0;j < organizationAdmin.length; ++j){
                    if(organizationAdmin[j].equals(String.valueOf(userId))){
                        flag = 0;
                        break;
                    }
                }
                if(flag == 1){
                    if(organizationAdminStr.equals("")){
                        organizationAdminStr = organizationAdminStr + userId;
                    } else {
                        organizationAdminStr = organizationAdminStr + "," + userId;
                    }
                }
            }
            organization.setAdminIdList(organizationAdminStr);
            organizationDAO.updateOrganization(organization);
        } else {
            AssessGroupPO group = assessGroupDAO.getGroupById(organizationId).get(0);
            String[] assessGroupAdmin = group.getAdminList().split(",");
            String assessGroupAdminStr = group.getAdminList();
            for(int i = 0;i < userInfo.size(); ++i){
                Map<String, Object> user = userInfo.get(i);
                int userId = (int)user.get("user_id");
                int flag = 1;
                for(int j = 0;j < assessGroupAdmin.length; ++j){
                    if(assessGroupAdmin[j].equals(String.valueOf(userId))){
                        flag = 0;
                        break;
                    }
                }
                if(flag == 1){
                    if(assessGroupAdminStr.equals("")){
                        assessGroupAdminStr = assessGroupAdminStr + userId;
                    } else {
                        assessGroupAdminStr = assessGroupAdminStr + "," + userId;
                    }
                }
            }
            group.setAdminList(assessGroupAdminStr);
            assessGroupDAO.updateAssessGroup(group);
        }
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo deleteOrganizationAdminUser(int userId, int organizationId, int isOrganization){
        // 除去userId 中的admin
        UserPO user = userDAO.getUserById(userId).get(0);
        String newAdminStr = "";
        String[] oldAdminList;
        oldAdminList = user.getAdminOrganization().split(",");
        for(int i = 0;i < oldAdminList.length; ++i){
            if(oldAdminList[i].equals(String.valueOf(userId)))continue;
            if(newAdminStr.equals("")){
                newAdminStr = newAdminStr + oldAdminList[i];
            } else {
                newAdminStr = newAdminStr + "," + oldAdminList[i];
            }
        }
        userDAO.updateUser(user);
        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            String[] oldOrganizationAdminList = organization.getAdminIdList().split(",");
            newAdminStr = "";
            for(int i = 0;i < oldOrganizationAdminList.length; ++i){
                if(oldOrganizationAdminList[i].equals(String.valueOf(userId)))continue;
                if(newAdminStr.equals("")){
                    newAdminStr = newAdminStr + oldOrganizationAdminList[i];
                } else {
                    newAdminStr = newAdminStr + "," + oldOrganizationAdminList[i];
                }
            }
            organization.setAdminIdList(newAdminStr);
            organizationDAO.updateOrganization(organization);
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            String[] oldAssessGroupAdminList = assessGroup.getAdminList().split(",");
            newAdminStr = "";
            for(int i = 0;i < oldAssessGroupAdminList.length; ++i){
                if(oldAssessGroupAdminList[i].equals(String.valueOf(userId)))continue;
                if(newAdminStr.equals("")){
                    newAdminStr = newAdminStr + oldAssessGroupAdminList[i];
                } else {
                    newAdminStr = newAdminStr + "," + oldAssessGroupAdminList[i];
                }
            }
            assessGroup.setAdminList(newAdminStr);
            assessGroupDAO.updateAssessGroup(assessGroup);
        }

        return new ResultInfo(200, "success", new HashMap<>());
    }


    public ResultInfo deleteOrganizationById(int organizationId){
        OrganizationPO nowOrganization = organizationDAO.getOrganizationById(organizationId).get(0);

        // 删除子节点
        String subOrganizationIdStr = nowOrganization.getSubOrganizationIdList();
        String subAssessGroupIdStr = nowOrganization.getSubGroupIdList();
        if(!subOrganizationIdStr.equals("")){
            String[] subOrganizationIdList = subOrganizationIdStr.split(",");
            for(int i = 0;i < subOrganizationIdList.length; ++i){
                deleteOrganizationById(parseInt(subOrganizationIdList[i]));
            }
        }
        if(!subAssessGroupIdStr.equals("")){
            String[] subAssessGroupIdList = subAssessGroupIdStr.split(",");
            for(int i = 0;i < subAssessGroupIdList.length; ++i){
                assessGroupService.deleteAssessGroupById(parseInt(subAssessGroupIdList[i]));
            }
        }
        // 删除父节点中的信息
        OrganizationPO superior = organizationDAO.getOrganizationById(nowOrganization.getSuperior()).get(0);
        String superiorSubOrganizationIdStr = superior.getSubOrganizationIdList();
        if(!superiorSubOrganizationIdStr.equals("")){
            String[] superiorSubOrganizationIdList = superiorSubOrganizationIdStr.split(",");
            String newSuperiorSubOrganizationIdStr = "";
            for(int i = 0;i < superiorSubOrganizationIdList.length; ++i){
                if(superiorSubOrganizationIdList[i].equals(String.valueOf(organizationId)))continue;
                if(newSuperiorSubOrganizationIdStr.equals("")){
                    newSuperiorSubOrganizationIdStr = newSuperiorSubOrganizationIdStr + superiorSubOrganizationIdList[i];
                } else {
                    newSuperiorSubOrganizationIdStr = newSuperiorSubOrganizationIdStr + "," + superiorSubOrganizationIdList[i];
                }
            }
            superior.setSubOrganizationIdList(newSuperiorSubOrganizationIdStr);
            organizationDAO.updateOrganization(superior);
        }
        // 删除将其作为考核机构 或者工作机构User的信息。
        List<UserPO> userList = userDAO.getUserByOrganizationId(organizationId);
        for(int i = 0;i < userList.size(); ++i){
            UserPO user = userList.get(i);
            if(user.getOrganization() == organizationId){
                user.setOrganization(999);
                userDAO.updateUser(user);
            }
            if(user.getAssessOrganizationId() == organizationId){
                user.setAssessOrganizationId(999);
                userDAO.updateUser(user);
            }
        }
        // 删除adminList 中用户的信息
        String adminIdStr = nowOrganization.getAdminIdList();
        if(!adminIdStr.equals("")){
            String[] adminIdList = adminIdStr.split(",");
            for(int i = 0;i < adminIdList.length; ++i){
                UserPO user = userDAO.getUserById(parseInt(adminIdList[i])).get(0);
                if(user.getAdminOrganization().equals(""))continue;
                String[] userAdminOrganizationIdList = user.getAdminOrganization().split(",");
                String newUserAdminOrganizationStr = "";
                for(int j = 0;j < userAdminOrganizationIdList.length; ++j){
                    if(userAdminOrganizationIdList[j].equals(String.valueOf(organizationId)))continue;
                    if(newUserAdminOrganizationStr.equals("")){
                        newUserAdminOrganizationStr = newUserAdminOrganizationStr + userAdminOrganizationIdList[j];
                    } else {
                        newUserAdminOrganizationStr = newUserAdminOrganizationStr + "," + userAdminOrganizationIdList[j];
                    }
                }
                user.setAdminOrganization(newUserAdminOrganizationStr);
                userDAO.updateUser(user);
            }
        }

        // 删除自己
        organizationDAO.deleteOrganization(organizationId);

        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo modifyOrganizationNameById(int organizationId, int isOrganization, String name){
        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            organization.setName(name);
            organizationDAO.updateOrganization(organization);
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            assessGroup.setName(name);
            assessGroupDAO.updateAssessGroup(assessGroup);
        }
        return new ResultInfo(200, "success", new HashMap<>());
    }
}
