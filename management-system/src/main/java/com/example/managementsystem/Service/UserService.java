package com.example.managementsystem.Service;

import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.DAO.OrganizationDAO;
import com.example.managementsystem.DAO.UserDAO;
import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.OrganizationPO;
import com.example.managementsystem.Model.UserPO;
import com.example.managementsystem.util.FileUtil;
import com.example.managementsystem.util.ResultInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;


@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public ResultInfo getUserById(int user_id){
        UserPO user = userDAO.getUserById(user_id).get(0);
        Map<String, Object> temp = new HashMap<>();
        temp.put("name", user.getName());
        if(user.getSex() == 1){
            temp.put("sex", "男");
        } else {
            temp.put("sex", "女");
        }
        temp.put("idNumber", user.getIdNumber());
        temp.put("phoneNumber", user.getUsername());
        temp.put("password", user.getPassword());
        temp.put("organization_id", user.getOrganization());
        temp.put("organization", organizationDAO.getOrganizationById(user.getOrganization()).get(0).getName());
        temp.put("position", user.getPosition());
        temp.put("assess_organization_id", user.getAssessOrganizationId());
        temp.put("assess_organization", organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0).getName());
        temp.put("assess_position", user.getAssessPosition());
        temp.put("assess_group", assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0).getName());
        temp.put("assess_group_id", user.getAssessGroupId());
        temp.put("group_start_time",formatter.format(user.getGroupStartTime()));
        return new ResultInfo(200, "success", temp);
    }

    public ResultInfo login(String username, String password, String isAdmin){
        if(isAdmin.equals("0")){
            List<UserPO> userPOS = userDAO.getUserByUsername(username);
            if(userPOS.size() == 0){
                Map<String, Object> result = new HashMap<>();
                result.put("state", 0);
                return new ResultInfo(200, "用户名不存在", result);
            } else {
                List<UserPO> Users = userDAO.getUserByUsernameAndPassword(username, password);
                if(Users.size() == 0){
                    Map<String, Object> result = new HashMap<>();
                    result.put("state", 0);
                    return new ResultInfo(200, "密码错误", result);
                } else {
                    UserPO user = Users.get(0);
                    Map<String, Object> result = new HashMap<>();
                    result.put("state", 1);
                    result.put("name", user.getName());
                    result.put("user_id",user.getUser_id());
                    List<OrganizationPO> organization = organizationDAO.getOrganizationById(user.getOrganization());
                    result.put("organization_name", organization.get(0).getName());
                    return new ResultInfo(200, "登录成功", result);
                }
            }
        } else {
            List<UserPO> userPOS = userDAO.getUserByUsername(username);
            if(userPOS.size() == 0){
                Map<String, Object> result = new HashMap<>();
                result.put("state", 0);
                return new ResultInfo(200, "用户名不存在", result);
            } else {
                List<UserPO> Users = userDAO.getUserByUsernameAndPassword(username, password);
                if (Users.size() == 0) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("state", 0);
                    return new ResultInfo(200, "密码错误", result);
                } else {
                    UserPO user = Users.get(0);
                    Map<String, Object> result = new HashMap<>();
                    if(user.getAdminAssessGroup().equals("") && user.getAdminOrganization().equals("")){
                        return new ResultInfo(200, "该用户不是管理员", result);
                    }
                    result.put("state", 1);
                    result.put("name", user.getName());
                    result.put("user_id", user.getUser_id());
                    List<OrganizationPO> organization = organizationDAO.getOrganizationById(user.getOrganization());
                    result.put("organization_name", organization.get(0).getName());
                    return new ResultInfo(200, "登录成功", result);
                }
            }
        }
    }

    public ResultInfo modifyUserInfo(Map<String, Object> userInfo){
        List<UserPO> userPOS = userDAO.getUserById((int)userInfo.get("user_id"));
        if(userPOS.size() == 0){
            Map<String, Object> result = new HashMap<>();
            result.put("state", 0);
            return new ResultInfo(200, "用户名不存在", result);
        } else {
            UserPO user = userPOS.get(0);
            if ((userInfo.get("old_password")).equals(user.getPassword())) {
                user.setPassword((String)userInfo.get("new_password"));
                userDAO.updateUser(user);
                Map<String, Object> result = new HashMap<>();
                result.put("state", 1);
                return new ResultInfo(200, "修改成功", result);
            } else {
                Map<String, Object> result = new HashMap<>();
                result.put("state", 0);
                return new ResultInfo(200, "旧密码错误", result);
            }

        }
    }

    public ResultInfo deleteUserById(int userId){
        // 删除 user 关联信息
        UserPO user = userDAO.getUserById(userId).get(0);

        // Organization
        String[] adminOrganizationIdList = user.getAdminOrganization().split(",");
        for(int i = 0;i < adminOrganizationIdList.length; ++i){
            OrganizationPO organization = organizationDAO.getOrganizationById(parseInt(adminOrganizationIdList[i])).get(0);
            String[] adminIdList = organization.getAdminIdList().split(",");
            List<String> newAdminIdList = new ArrayList<>();
            for(int j = 0;j < adminIdList.length; ++j){
                if(parseInt(adminIdList[j]) != userId){
                    newAdminIdList.add(adminIdList[j]);
                }
            }
            String newAdminIdStr = "";
            for(int j = 0;j < newAdminIdList.size(); ++j){
                if(j == 0) newAdminIdStr += newAdminIdList.get(j);
                else newAdminIdStr += "," + newAdminIdList.get(j);
            }
            organization.setAdminIdList(newAdminIdStr);
            organizationDAO.updateOrganization(organization);
        }

        // AssessGroup
        String[] adminAssessGroupIdList = user.getAdminAssessGroup().split(",");
        for(int i = 0;i < adminAssessGroupIdList.length; ++i){
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(parseInt(adminAssessGroupIdList[i])).get(0);
            String[] adminIdList = assessGroup.getAdminList().split(",");
            List<String> newAdminIdList = new ArrayList<>();
            for(int j = 0;j < adminIdList.length; ++j){
                if(parseInt(adminIdList[j]) != userId){
                    newAdminIdList.add(adminIdList[j]);
                }
            }
            String newAdminIdStr = "";
            for(int j = 0;j < newAdminIdList.size(); ++j){
                if(j == 0) newAdminIdStr += newAdminIdList.get(j);
                else newAdminIdStr += "," + newAdminIdList.get(j);
            }
            assessGroup.setAdminList(newAdminIdStr);
            assessGroupDAO.updateAssessGroup(assessGroup);
        }

        userDAO.deleteUserById(userId);
        return new ResultInfo(200, "success", new Object());
    }

    public ResultInfo createUser(Map<String, Object> userInfo){
        UserPO user = new UserPO();

        user.setUsername((String)userInfo.get("username"));
        user.setPassword((String)userInfo.get("password"));
        user.setIdNumber((String)userInfo.get("idNumber"));
        user.setName((String)userInfo.get("name"));
        user.setOrganization((int)userInfo.get("be_organization_id"));
        user.setAssessOrganizationId((int)userInfo.get("be_assess_organization_id"));
        if((int)userInfo.get("is_organization") == 0){
            user.setAssessGroupId((int)userInfo.get("organization_id"));
        }
        user.setGroupStartTime(new Date(System.currentTimeMillis()));
        user.setPosition((String)userInfo.get("position"));
        user.setAssessPosition((String)userInfo.get("assess_position"));
        user.setSex((int)userInfo.get("sex"));
        int userId = userDAO.createUser(user);
        if(userId == -1){
            return new ResultInfo(200, "用户名已存在!", new HashMap<>());
        }
        Map<String, Object> result = new HashMap<>();
        result.put("state", 1);
        result.put("user_id", userId);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAdminOrganizationByUserId(int userId){
        UserPO user = userDAO.getUserById(userId).get(0);
        String[] adminOrganization = user.getAdminOrganization().split(",");
        List<Map<String, Object>> tempList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        for(int i = 0;i < adminOrganization.length; ++i){
            int adminOrganizationId = parseInt(adminOrganization[i]);
            OrganizationPO organization = organizationDAO.getOrganizationById(adminOrganizationId).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", organization.getName());
            temp.put("organization_id", adminOrganizationId);
            tempList.add(temp);
        }
        result.put("admin_organization", tempList);
        return new ResultInfo(200, "success", result);
    }

    public List<Integer> getTotalManageUserIdByAdminUserId(int userId){
        List<Integer> adminAssessGroupId = new ArrayList<>();
        UserPO user = userDAO.getUserById(userId).get(0);
        String[] adminOrganizationStr = user.getAdminOrganization().split(",");
        String[] adminAssessGroupStr = user.getAdminAssessGroup().split(",");
        for(int i = 0;i < adminAssessGroupStr.length; ++i){
            adminAssessGroupId.add(parseInt(adminAssessGroupStr[i]));
        }

        // 获取每个管理到的机构 所管辖到的 考核组的Id
        for(int i = 0;i < adminOrganizationStr.length;++i){
            List<Integer> tempAdminAssessGroupId = assessGroupDAO.getTotalManageGroupIdByOrganizationId(parseInt(adminOrganizationStr[i]));
            for(int j = 0;j < tempAdminAssessGroupId.size(); ++j){
                adminAssessGroupId.add(tempAdminAssessGroupId.get(j));
            }
        }
        // 对考核组去重并返回
        adminAssessGroupId = adminAssessGroupId.stream().distinct().collect(Collectors.toList());

        // 获取每个考核组中的人员id
        List<Integer> userIdList = new ArrayList<>();
        for(int i = 0;i < adminAssessGroupId.size(); ++i){
            List<UserPO> users = userDAO.getUserByAssessGroupId(adminAssessGroupId.get(i));
            for(int j = 0;j < users.size(); ++j){
                userIdList.add(users.get(j).getUser_id());
            }
        }
        // 对userId 去重
        return userIdList.stream().distinct().collect(Collectors.toList());
    }

    public List<Integer> getTotalManageUserIdByAdminUserIdAndOrganizationId(int userId, int organizationId, int isOrganization){

        List<Integer> userIdList = new ArrayList<>();
        // 获取某一机构下所有 考察组 的Id
        List<Integer> assessGroupIdList = new ArrayList<>();
        if(isOrganization == 1){
            // 通过organizationId 获取所有 User
            List<UserPO> users = userDAO.getUserByOrganizationId(organizationId);
            for(int i = 0;i < users.size(); ++i){
                userIdList.add(users.get(i).getUser_id());
            }
            // 递归处理子节点
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            String[] subOrganizationIdList = organization.getSubOrganizationIdList().split(",");
            for(int i = 0; i < subOrganizationIdList.length; ++i){
                if(subOrganizationIdList[i].equals(""))continue;
                userIdList.addAll(getTotalManageUserIdByAdminUserIdAndOrganizationId(userId, parseInt(subOrganizationIdList[i]), 1));
            }
            String[] subAssessGroupIdList = organization.getSubGroupIdList().split(",");
            for(int i = 0;i < subAssessGroupIdList.length; ++i){
                if(subAssessGroupIdList[i].equals(""))continue;
                userIdList.addAll(getTotalManageUserIdByAdminUserIdAndOrganizationId(userId, parseInt(subAssessGroupIdList[i]), 0));
            }
        } else {
            List<UserPO> users = userDAO.getUserByAssessGroupId(organizationId);
            for(int i = 0;i < users.size(); ++i){
                userIdList.add(users.get(i).getUser_id());
            }
        }

        // 对userId 去重
        return userIdList.stream().distinct().collect(Collectors.toList());
    }

    public List<Integer> getTotalManageUserIdByOrganizationIdAndUserName(int organizationId, String username, int isOrganization){
        // 获取某一机构下所有 考察组 的Id
        List<Integer> assessGroupIdList = new ArrayList<>();
        if(isOrganization == 1){
            assessGroupIdList = assessGroupDAO.getTotalManageGroupIdByOrganizationId(organizationId);
        } else {
            assessGroupIdList.add(organizationId);
        }
        // 根据 assessGroupIdList 获取 userId
        List<Integer> userIdList = new ArrayList<>();
        for(int i = 0;i < assessGroupIdList.size(); ++i){
            List<UserPO> users = userDAO.getUserByAssessGroupId(assessGroupIdList.get(i));
            for(int j = 0;j < users.size(); ++j){
                userIdList.add(users.get(j).getUser_id());
            }
        }
        // 对userId 去重
        return userIdList.stream().distinct().collect(Collectors.toList());
    }

    public ResultInfo getTotalManageUserIdByAdminUserId(int userId, int page, int limit){
        List<Integer> userIdList = getTotalManageUserIdByAdminUserId(userId);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, userIdList.size()); ++i){
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
            temp.put("index", ++index);
            temp.put("name", user.getName());
            if(user.getSex() == 1){
                temp.put("sex", "男");
            } else {
                temp.put("sex", "女");
            }
            temp.put("idNumber", user.getIdNumber());
            temp.put("phoneNumber", user.getUsername());
            temp.put("password", user.getPassword());
            temp.put("organization_id", user.getOrganization());
            temp.put("organization", organizationDAO.getOrganizationById(user.getOrganization()).get(0).getName());
            temp.put("position", user.getPosition());
            temp.put("assess_organization_id", user.getAssessOrganizationId());
            temp.put("assess_organization", organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0).getName());
            temp.put("assess_position", user.getAssessPosition());
            temp.put("assess_group", assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0).getName());
            temp.put("assess_group_id", user.getAssessGroupId());
            temp.put("group_start_time",formatter.format(user.getGroupStartTime()));
            tempList.add(temp);
        }
        result.put("count", userIdList.size());
        result.put("result", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAdminUserByOrganizationId(int userId, int organizationId, int isOrganization, int page, int limit){
        List<Integer> userIdList = getTotalManageUserIdByAdminUserIdAndOrganizationId(userId, organizationId, isOrganization);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, userIdList.size()); ++i){
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
            temp.put("index", ++index);
            temp.put("name", user.getName());
            if(user.getSex() == 1){
                temp.put("sex", "男");
            } else {
                temp.put("sex", "女");
            }
            temp.put("user_id", user.getUser_id());
            temp.put("idNumber", user.getIdNumber());
            temp.put("phoneNumber", user.getUsername());
            temp.put("password", user.getPassword());
            temp.put("organization_id", user.getOrganization());
            temp.put("organization", organizationDAO.getOrganizationById(user.getOrganization()).get(0).getName());
            temp.put("position", user.getPosition());
            temp.put("assess_organization_id", user.getAssessOrganizationId());
            temp.put("assess_organization", organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0).getName());
            temp.put("assess_position", user.getAssessPosition());
            if(user.getAssessGroupId() != null){
                temp.put("assess_group", assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0).getName());
                temp.put("assess_group_id", user.getAssessGroupId());
                temp.put("group_start_time",formatter.format(user.getGroupStartTime()));
            }
            tempList.add(temp);
        }
        result.put("count", userIdList.size());
        result.put("result", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getTotalUser(int page, int limit){
        List<UserPO> users = userDAO.getTotalUser();
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, users.size()); ++i){
            Map<String, Object> temp = new HashMap<>();
            UserPO user = users.get(i);
            temp.put("index", ++index);
            temp.put("name", user.getName());
            if(user.getSex() == 1){
                temp.put("sex", "男");
            } else {
                temp.put("sex", "女");
            }
            temp.put("user_id", user.getUser_id());
            temp.put("idNumber", user.getIdNumber());
            temp.put("phoneNumber", user.getUsername());
            temp.put("password", user.getPassword());
            temp.put("organization_id", user.getOrganization());
            temp.put("organization", organizationDAO.getOrganizationById(user.getOrganization()).get(0).getName());
            temp.put("position", user.getPosition());
            temp.put("assess_organization_id", user.getAssessOrganizationId());
            temp.put("assess_organization", organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0).getName());
            temp.put("assess_position", user.getAssessPosition());
            if(user.getAssessGroupId() != null){
                temp.put("assess_group", assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0).getName());
                temp.put("assess_group_id", user.getAssessGroupId());
                temp.put("group_start_time",formatter.format(user.getGroupStartTime()));
            }
            tempList.add(temp);
        }
        result.put("count", users.size());
        result.put("result", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getUserByOrganizationIdAndName(int userId, int organizationId, int isOrganization
                                                    ,String username, int page, int limit){
        List<Integer> userIdList = getTotalManageUserIdByAdminUserIdAndOrganizationId(userId, organizationId, isOrganization);
        List<Integer> finalUserIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
            if(user.getName().equals(username)){
                finalUserIdList.add(userIdList.get(i));
            }
        }
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, finalUserIdList.size()); ++i){
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(finalUserIdList.get(i)).get(0);
            temp.put("index", ++index);
            temp.put("name", user.getName());
            if(user.getSex() == 1){
                temp.put("sex", "男");
            } else {
                temp.put("sex", "女");
            }
            temp.put("user_id", user.getUser_id());
            temp.put("idNumber", user.getIdNumber());
            temp.put("phoneNumber", user.getUsername());
            temp.put("password", user.getPassword());
            temp.put("organization_id", user.getOrganization());
            temp.put("organization", organizationDAO.getOrganizationById(user.getOrganization()).get(0).getName());
            temp.put("position", user.getPosition());
            temp.put("assess_organization_id", user.getAssessOrganizationId());
            temp.put("assess_organization", organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0).getName());
            temp.put("assess_position", user.getAssessPosition());
            temp.put("assess_group", assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0).getName());
            temp.put("assess_group_id", user.getAssessGroupId());
            temp.put("group_start_time",formatter.format(user.getGroupStartTime()));
            tempList.add(temp);
        }
        result.put("count", finalUserIdList.size());
        result.put("result", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo addAdminByOrganizationId(int organizationId, int isOrganization,
                                               String name, String idNumber, String username, int sex, String password
                                                , int beOrganizationId, String position, int beAssessOrganizationId, String assessPosition
                                               ){
        UserPO user = new UserPO();
        user.setName(name);
        user.setPassword(password);
        user.setSex(sex);
        user.setPosition(position);
        user.setAssessPosition(assessPosition);
        user.setAssessOrganizationId(beAssessOrganizationId);
        user.setOrganization(beOrganizationId);
        user.setUsername(username);
        user.setIdNumber(idNumber);
        int userId = userDAO.createUser(user);
        if(userId == -1){
            return new ResultInfo(200, "用户名已存在!", new HashMap<>());
        }
        // 更新关联的组织的adminList
        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            String adminList = organization.getAdminIdList();
            if(adminList.equals("")){
                adminList = adminList + userId;
            } else {
                adminList = adminList + "," + userId;
            }
            organization.setAdminIdList(adminList);
            organizationDAO.updateOrganization(organization);
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            String adminList = assessGroup.getAdminList();
            if(adminList.equals("")){
                adminList = adminList + userId;
            } else {
                adminList = adminList + "," + userId;
            }
            assessGroup.setAdminList(adminList);
            assessGroupDAO.updateAssessGroup(assessGroup);
        }
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getOrganizationAdminById(int organizationId, int isOrganization, int page, int limit){
        String[] adminIdList;
        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            adminIdList = organization.getAdminIdList().split(",");
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            adminIdList = assessGroup.getAdminList().split(",");
        }
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(adminIdList.length, page * limit); ++i){
            if(adminIdList[i].equals(""))continue;
            UserPO user = userDAO.getUserById(parseInt(adminIdList[i])).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("name", user.getName());
            if(user.getSex() == 1){
                temp.put("sex", "男");
            } else {
                temp.put("sex", "女");
            }
            temp.put("user_id", user.getUser_id());
            temp.put("idNumber", user.getIdNumber());
            temp.put("phoneNumber", user.getUsername());
            temp.put("password", user.getPassword());
            temp.put("organization_id", user.getOrganization());
            temp.put("organization", organizationDAO.getOrganizationById(user.getOrganization()).get(0).getName());
            temp.put("position", user.getPosition());
            temp.put("assess_organization_id", user.getAssessOrganizationId());
            temp.put("assess_organization", organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0).getName());
            temp.put("assess_position", user.getAssessPosition());
            if(user.getAssessGroupId() != null) {
                temp.put("assess_group", assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0).getName());
                temp.put("assess_group_id", user.getAssessGroupId());
                temp.put("group_start_time", formatter.format(user.getGroupStartTime()));
            }
            tempList.add(temp);
        }
        if(adminIdList[0].equals("")){
            result.put("count", 0);
        } else {
            result.put("count", adminIdList.length);
        }

        result.put("result", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo updateUserInfoByIdList(List<Map<String, Object>> userInfoList){
        for(int i = 0;i < userInfoList.size(); ++i){
            Map<String, Object> userInfo = userInfoList.get(i);
            UserPO user = userDAO.getUserById((int)userInfo.get("user_id")).get(0);
            user.setPassword((String)userInfo.get("password"));
            if(userInfo.get("assess_group_id") != null){
                // 修改 old_assess_group 的 member list 字段
                if(user.getAssessGroupId() != null){
                    AssessGroupPO assessGroup = assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0);
                    String memberList = assessGroup.getMemberList();
                    if(memberList != null){
                        if(!memberList.equals("")){
                            String[] memberIdList = memberList.split(",");
                            String newMemberIdList = "";
                            for(int j = 0; j < memberIdList.length; ++j){
                                if(memberIdList[j].equals(String.valueOf(user.getUser_id()))) continue;
                                if(newMemberIdList.equals("")){
                                    newMemberIdList = newMemberIdList + memberIdList[j];
                                } else {
                                    newMemberIdList = newMemberIdList + "," + memberIdList[j];
                                }
                            }
                            assessGroup.setMemberList(newMemberIdList);
                            assessGroupDAO.updateAssessGroup(assessGroup);
                        }
                    }
                }

                // 在新的 assess_group 里 加id
                AssessGroupPO newAssessGroup = assessGroupDAO.getGroupById((int)userInfo.get("assess_group_id")).get(0);
                String newAssessGroupMemberList = newAssessGroup.getMemberList();
                if(newAssessGroupMemberList.equals("")){
                    newAssessGroupMemberList = newAssessGroupMemberList + user.getUser_id();
                } else {
                    newAssessGroupMemberList = newAssessGroupMemberList + "," + user.getUser_id();
                }
                newAssessGroup.setMemberList(newAssessGroupMemberList);
                assessGroupDAO.updateAssessGroup(newAssessGroup);

                // 更新本身
                user.setAssessGroupId((int)userInfo.get("assess_group_id"));
                user.setGroupStartTime(new Date());
            }
            if(userInfo.get("organization_id") != null){
                user.setOrganization((int)userInfo.get("organization_id"));
            }
            if(userInfo.get("assess_organization_id") != null){
                user.setAssessOrganizationId((int)userInfo.get("assess_organization_id"));
            }
            userDAO.updateUser(user);
        }
        return new ResultInfo(200,"success", new HashMap<>());
    }

    public ResultInfo uploadUser(MultipartFile file, int organizationId, int isOrganization){
        ArrayList<ArrayList<String>> info = FileUtil.analysis(file);
        List<Map<String, Object> > isExistUser = new ArrayList<>();
        for(int i = 0;i < info.size(); ++i){
            UserPO user = new UserPO();
            if(isOrganization == 1){
                user.setOrganization(organizationId);
                user.setAssessOrganizationId(organizationId);
            } else {
                user.setOrganization(999);
                user.setAssessOrganizationId(999);
                user.setAssessGroupId(organizationId);
                user.setGroupStartTime(new Date());
            }

            user.setPosition("无");
            user.setAssessPosition("无");
            for(int j = 0; j < info.get(0).size(); ++j){
                if(j == 1){
                    user.setName(info.get(i).get(j));
                } else if(j == 2){
                    user.setUsername(info.get(i).get(j));
                } else if(j == 3){
                    user.setPassword(info.get(i).get(j));
                } else if(j == 4){
                    user.setIdNumber(info.get(i).get(j));
                } else if(j == 5) {
                    if (info.get(i).get(j).equals("男")) {
                        user.setSex(1);
                    } else {
                        user.setSex(0);
                    }
                }
            }
            int userId = userDAO.createUser(user);
            if(userId == -1) {
                Map<String, Object> temp = new HashMap<>();
                temp.put("name", user.getName());
                temp.put("username", user.getUsername());
                isExistUser.add(temp);
                continue;
            }
            if(isOrganization == 0){
                AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
                String memberStr = assessGroup.getMemberList();
                if(memberStr.equals("")){
                    memberStr = memberStr + userId;
                } else {
                    memberStr = memberStr + "," + userId;
                }
                assessGroup.setMemberList(memberStr);
                assessGroupDAO.updateAssessGroup(assessGroup);
            }
        }
        return new ResultInfo(200, "success", isExistUser);
    }
}
