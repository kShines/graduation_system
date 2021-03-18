package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.AchievementPO;
import com.example.managementsystem.Model.PlanPO;
import com.example.managementsystem.Model.SummaryPO;
import com.example.managementsystem.Model.tempQueryEntity.LongNumberEntity;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AchievementRepository extends CrudRepository<AchievementPO, Integer> {

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM AchievementPO t WHERE t.creator = :userId")
    List<LongNumberEntity> getAchievementAmountByUserId(@Param("userId") int userId);

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM AchievementPO t WHERE t.creator = :userId AND t.year=:year AND t.quarter=:quarter")
    List<LongNumberEntity> getAchievementAmountByUserIdAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter);

    @Query("SELECT t FROM AchievementPO t WHERE t.creator = :userId")
    Page<AchievementPO> getAchievementByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT t FROM AchievementPO t WHERE t.creator = :userId")
    List<AchievementPO> getTotalAchievementByUserId(@Param("userId") int userId);

    @Query("SELECT t FROM AchievementPO t WHERE t.creator = :userId AND t.year = :year AND t.quarter = :quarter")
    Page<AchievementPO> getAchievementsByUserIdAnAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter, Pageable pageable);

    @Query("SELECT t FROM AchievementPO t WHERE t.creator = :userId AND t.year = :year AND t.quarter = :quarter")
    List<AchievementPO> getTotalAchievementsByUserIdAnAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter);

    @Query("SELECT t FROM AchievementPO t WHERE t.achievement_id = :achievementId")
    List<AchievementPO> getAchievementById(int achievementId);

    @Query("SELECT new QueryEntity(t.achievement_id, t.title, u.name) FROM AchievementPO t LEFT JOIN UserPO u ON t.creator=u.user_id WHERE u.name=:userName AND u.assessGroupId=:groupId")
    List<QueryEntity> getAchievementByGroupIdAndUserName(@Param("userName") String userName, @Param("groupId") int groupId);

    @Query(value="SELECT * FROM achievement WHERE creator=?1 AND create_time >= ?2 AND create_time <= ?3", nativeQuery = true)
    List<AchievementPO> getAchievementByUserIdAndTimeLimit(int userId, String beginTime, String endTime);
}
