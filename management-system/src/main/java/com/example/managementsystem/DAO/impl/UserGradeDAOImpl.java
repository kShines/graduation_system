package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.UserGradeDAO;
import com.example.managementsystem.Model.UserGradePO;
import com.example.managementsystem.Model.repository.UserGradeRepository;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGradeDAOImpl implements UserGradeDAO {

    @Autowired
    private UserGradeRepository userGradeRepository;

    public List<UserGradePO> getUserGradeByAssessBatchId(int assessBatchId) {
        return userGradeRepository.getUserGradeByAssessBatchId(assessBatchId);
    }

    public List<QueryEntity> getUserGradeByAssessBatchIdAndUserName(int assessBatchId, String userName) {
        return userGradeRepository.getUserGradeByAssessBatchIdAndUserName(userName, assessBatchId);
    }

    public void deleteUserGradeById(int userGradeId) {
        userGradeRepository.deleteById(userGradeId);
    }

    public void createUserGrade(UserGradePO userGrade) {
        userGradeRepository.save(userGrade);
    }

    public List<UserGradePO> getUserGrade(int userGradeId) {
        return userGradeRepository.getUserGradeById(userGradeId);
    }

    public void updateUserGrade(UserGradePO userGrade) {
        userGradeRepository.save(userGrade);
    }
}
