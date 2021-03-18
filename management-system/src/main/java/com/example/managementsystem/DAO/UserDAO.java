package com.example.managementsystem.DAO;

import com.example.managementsystem.Model.UserPO;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    List<UserPO> getUserById(int userId);

    List<UserPO> getUserByUsername(String username);

    List<UserPO> getUserByUsernameAndPassword(String username, String password);

    void updateUser(UserPO user);

    void deleteUserById(int userId);

    int createUser(UserPO user);

    List<UserPO> getUserByAssessGroupId(int assessGroupId);

    Boolean judgePermissionOnAnOrganization(int userId, int organizationId, int isOrganization);

    List<UserPO> getTotalUser();

    List<UserPO> getUserByOrganizationId(int userId);

}
