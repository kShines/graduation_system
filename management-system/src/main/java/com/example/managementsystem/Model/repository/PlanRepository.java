package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.PlanPO;
import com.example.managementsystem.Model.tempQueryEntity.LongNumberEntity;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends CrudRepository<PlanPO, Integer> {

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM PlanPO t WHERE t.creator = :userId")
    List<LongNumberEntity> getPlanAmountByUserId(@Param("userId") int userId);

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM PlanPO t WHERE t.creator = :userId AND t.year=:year AND t.quarter=:quarter")
    List<LongNumberEntity> getPlanAmountByUserIdAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter);

    @Query("SELECT t FROM PlanPO t WHERE t.creator = :userId")
    Page<PlanPO> getPlansByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT t FROM PlanPO t WHERE t.creator = :userId")
    List<PlanPO> getTotalPlanByUserId(@Param("userId") int userId);

    @Query("SELECT t FROM PlanPO t WHERE t.quarter = :quarter AND t.creator = :userId AND t.year = :year")
    Page<PlanPO> getPlansByUserIdAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter, Pageable pageable);

    @Query("SELECT t FROM PlanPO t WHERE t.quarter = :quarter AND t.creator = :userId AND t.year = :year")
    List<PlanPO> getTotalPlanByUserIdAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter);

    @Query("SELECT t FROM PlanPO t WHERE t.plan_id = :planId")
    List<PlanPO> getPlanById(@Param("planId") int planId);

    @Query("SELECT new QueryEntity(t.plan_id, t.title, u.name) FROM PlanPO t LEFT JOIN UserPO u ON t.creator=u.user_id WHERE u.name=:userName AND u.assessGroupId = :groupId")
    List<QueryEntity> getPlanByName(@Param("userName") String userName,@Param("groupId") int groupId);

    @Query(value="SELECT * FROM plan WHERE creator=?1 AND create_time >= ?2 AND create_time <= ?3", nativeQuery = true)
    List<PlanPO> getPlanByUserIdAndTimeLimit(int userId, String beginTime, String endTime);
}
