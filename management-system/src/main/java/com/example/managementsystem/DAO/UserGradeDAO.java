package com.example.managementsystem.DAO;

import com.example.managementsystem.Model.UserGradePO;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGradeDAO {
    List<UserGradePO> getUserGradeByAssessBatchId(int assessBatchId);

    List<QueryEntity> getUserGradeByAssessBatchIdAndUserName(int assessBatchId, String userName);

    void deleteUserGradeById(int userGradeId);

    void createUserGrade(UserGradePO userGrade);

    List<UserGradePO> getUserGrade(int userGradeId);

    void updateUserGrade(UserGradePO userGrade);

}
