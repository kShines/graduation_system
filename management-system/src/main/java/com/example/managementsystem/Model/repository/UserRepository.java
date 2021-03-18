package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.UserPO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<UserPO, Integer> {

    @Query(value = "SELECT t FROM UserPO t WHERE t.user_id = :userId")
    List<UserPO> getUserById(@Param("userId")int userId);

    @Query(value = "SELECT t FROM UserPO t WHERE t.username = :username")
    List<UserPO> getUserByUsername(@Param("username")String username);

    @Query(value = "SELECT t FROM UserPO t WHERE t.username = :username AND t.password = :password")
    List<UserPO> getUserByUsernameAndPassword(@Param("username")String username, @Param("password")String password);

    @Query(value = "SELECT t FROM UserPO t WHERE t.assessGroupId = :assessGroupId")
    List<UserPO> getUserByAssessGroupId(@Param("assessGroupId") int assessGroupId);

    @Query(value = "SELECT t FROM UserPO t")
    List<UserPO> getTotalUser();

    @Query(value = "SELECT t FROM UserPO t WHERE t.organization=:organizationId or t.assessOrganizationId=:organizationId")
    List<UserPO> getUserByOrganizationId(@Param("organizationId") int organizationId);

}
