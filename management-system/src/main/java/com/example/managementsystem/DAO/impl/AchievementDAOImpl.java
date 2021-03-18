package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.AchievementDAO;
import com.example.managementsystem.Model.AchievementPO;
import com.example.managementsystem.Model.repository.AchievementRepository;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AchievementDAOImpl implements AchievementDAO {

    @Autowired
    private AchievementRepository achievementRepository;

    public int getAchievementAmountByUserId(int userId) {
        return achievementRepository.getAchievementAmountByUserId(userId).get(0).getNumber().intValue();
    }

    public int getAchievementAmountByUserIdAndQuarter(int userId, int year, int quarter) {
        return achievementRepository.getAchievementAmountByUserIdAndQuarter(userId, year, quarter).get(0).getNumber().intValue();
    }

    public List<AchievementPO> getAchievementByUserId(int userId, int page, int limit) {
        return achievementRepository.getAchievementByUserId(userId, PageRequest.of(page, limit)).getContent();
    }

    public List<AchievementPO> getAchievementsByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit) {
        return achievementRepository.getAchievementsByUserIdAnAndQuarter(userId, year, quarter, PageRequest.of(page, limit)).getContent();
    }

    public List<AchievementPO> getTotalAchievementByUserId(int userId) {
        return achievementRepository.getTotalAchievementByUserId(userId);
    }

    public List<AchievementPO> getTotalAchievementByUserIdAndQuarter(int userId, int year, int quarter) {
        return achievementRepository.getTotalAchievementsByUserIdAnAndQuarter(userId, year, quarter);
    }

    public List<AchievementPO> getAchievementById(int achievementId) {
        return achievementRepository.getAchievementById(achievementId);
    }

    public void insertAchievement(Map<String, Object> achievementInfo) {
        AchievementPO achievement = new AchievementPO();
        achievement.setCreator((int)achievementInfo.get("user_id"));
        achievement.setQuarter((int)achievementInfo.get("quarter"));
        achievement.setYear((int)achievementInfo.get("year"));
        achievement.setTitle((String)achievementInfo.get("title"));
        achievement.setText_1((String)achievementInfo.get("text_1"));
        achievement.setText_2((String)achievementInfo.get("text_2"));
        achievement.setText_3((String)achievementInfo.get("text_3"));
        achievement.setCreateTime(new Date(System.currentTimeMillis()));
        achievement.setUpdateTime(new Date(System.currentTimeMillis()));
        achievementRepository.save(achievement);
    }

    public void updateAchievement(Map<String, Object> achievementInfo){
        AchievementPO achievement = achievementRepository.getAchievementById((int)achievementInfo.get("achievement_id")).get(0);
        achievement.setMonth((int)achievementInfo.get("month"));
        achievement.setQuarter((int)achievementInfo.get("quarter"));
        achievement.setYear((int)achievementInfo.get("year"));
        achievement.setTitle((String)achievementInfo.get("title"));
        achievement.setText_1((String)achievementInfo.get("text_1"));
        achievement.setText_2((String)achievementInfo.get("text_2"));
        achievement.setText_3((String)achievementInfo.get("text_3"));
        achievement.setUpdateTime(new Date(System.currentTimeMillis()));
        achievementRepository.save(achievement);
    }

    public void deleteAchievementById(int achievementId) {
        achievementRepository.deleteById(achievementId);
    }

    public List<QueryEntity> getAchievementByGroupIdAndUserName(String userName, int groupId) {
        return achievementRepository.getAchievementByGroupIdAndUserName(userName, groupId);
    }

    public List<AchievementPO> getAchievementByUserIdAndTimeLimit(int userId, String beginTime, String endTime) {
        return achievementRepository.getAchievementByUserIdAndTimeLimit(userId, beginTime, endTime);
    }

}
