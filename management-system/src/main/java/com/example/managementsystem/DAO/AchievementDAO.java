package com.example.managementsystem.DAO;

import com.example.managementsystem.Model.AchievementPO;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AchievementDAO {

    int getAchievementAmountByUserId(int userId);

    int getAchievementAmountByUserIdAndQuarter(int userId, int year,int quarter);

    List<AchievementPO> getAchievementByUserId(int userId, int page, int limit);

    List<AchievementPO> getAchievementsByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit);

    List<AchievementPO> getTotalAchievementByUserId(int userId);

    List<AchievementPO> getTotalAchievementByUserIdAndQuarter(int userId, int year, int quarter);

    List<AchievementPO> getAchievementById(int achievementId);

    void insertAchievement(Map<String, Object> achievementInfo);

    void updateAchievement(Map<String, Object> achievementInfo);

    void deleteAchievementById(int achievementId);

    List<QueryEntity> getAchievementByGroupIdAndUserName(String userName, int groupId);

    List<AchievementPO> getAchievementByUserIdAndTimeLimit(int userId, String beginTime, String endTime);
}
