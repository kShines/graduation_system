package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.UserDAO;
import com.example.managementsystem.Model.UserPO;
import com.example.managementsystem.Model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Integer.parseInt;


@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    public List<UserPO> getUserById(int userId) {
        return userRepository.getUserById(userId);
    }

    public List<UserPO> getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public List<UserPO> getUserByUsernameAndPassword(String username, String password) {
        return userRepository.getUserByUsernameAndPassword(username, password);
    }

    public void updateUser(UserPO user) {
        userRepository.save(user);
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
    }

    public int createUser(UserPO user) {
        List<UserPO> users = userRepository.getUserByUsername(user.getUsername());
        if(users.size() != 0){
            return -1;
        } else {
            if (user.getAdminAssessGroup() == null) user.setAdminAssessGroup("");
            if (user.getAdminOrganization() == null) user.setAdminOrganization("");
            userRepository.save(user);

            return user.getUser_id();
        }
    }

    public List<UserPO> getUserByAssessGroupId(int assessGroupId){
        return userRepository.getUserByAssessGroupId(assessGroupId);
    }

    // 判断管理员在某个机构下是否有权限
    public Boolean judgePermissionOnAnOrganization(int userId, int organizationId, int isOrganization) {
        UserPO user = userRepository.getUserById(userId).get(0);
        String[] adminOrganizationIdList = user.getAdminOrganization().split(",");
        String[] adminAssessGroupIdList = user.getAdminAssessGroup().split(",");
        Boolean result = false;
        if(isOrganization == 0){
            for(int i = 0;i < adminAssessGroupIdList.length; ++i){
                if(organizationId == parseInt(adminAssessGroupIdList[i])){
                    result = true;
                }
            }
        } else {
            for(int i = 0;i < adminOrganizationIdList.length; ++i){
                if(organizationId == parseInt(adminOrganizationIdList[i])){
                    result = true;
                }
                result |= judgePermissionOnAnOrganization(userId, organizationId, isOrganization);
            }
        }
        return result;
    }

    public List<UserPO> getTotalUser() {
        return userRepository.getTotalUser();
    }

    public List<UserPO> getUserByOrganizationId(int organizationId) {
        return userRepository.getUserByOrganizationId(organizationId);
    }
}
