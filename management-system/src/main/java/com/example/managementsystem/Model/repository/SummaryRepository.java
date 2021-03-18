package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.PlanPO;
import com.example.managementsystem.Model.SummaryPO;
import com.example.managementsystem.Model.tempQueryEntity.LongNumberEntity;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SummaryRepository extends CrudRepository<SummaryPO, Integer> {

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM SummaryPO t WHERE t.creator = :userId")
    List<LongNumberEntity> getSummaryAmountByUserId(@Param("userId") int userId);

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM SummaryPO t WHERE t.creator = :userId AND t.year=:year AND t.quarter=:quarter")
    List<LongNumberEntity> getSummaryAmountByUserIdAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter);

    @Query("SELECT t FROM SummaryPO t WHERE t.creator = :userId")
    Page<SummaryPO> getSummaryByUserId(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT t FROM SummaryPO t WHERE t.creator = :userId")
    List<SummaryPO> getTotalSummaryByUserId(@Param("userId") int userId);

    @Query("SELECT t FROM SummaryPO t WHERE t.creator = :userId AND t.year = :year AND t.quarter = :quarter")
    Page<SummaryPO> getSummaryByUserIdAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter, Pageable pageable);

    @Query("SELECT t FROM SummaryPO t WHERE t.creator = :userId AND t.year = :year AND t.quarter = :quarter")
    List<SummaryPO> getTotalSummaryByUserIdAndQuarter(@Param("userId") int userId, @Param("year") int year, @Param("quarter") int quarter);

    @Query("SELECT t FROM SummaryPO t WHERE t.summary_id = :summaryId")
    List<SummaryPO> getSummaryById(@Param("summaryId") int summaryId);

    @Query("SELECT new QueryEntity(t.summary_id, t.title, u.name) FROM SummaryPO t LEFT JOIN UserPO u ON t.creator=u.user_id WHERE u.name=:userName AND u.assessGroupId = :groupId")
    List<QueryEntity> getSummaryByGroupIdAndUserName(@Param("userName") String userName,@Param("groupId") int groupId);

    @Query(value="SELECT * FROM summary WHERE creator=?1 AND create_time >= ?2 AND create_time <= ?3", nativeQuery = true)
    List<SummaryPO> getSummaryByUserIdAndTimeLimit(int userId, String beginTime, String endTime);
}
