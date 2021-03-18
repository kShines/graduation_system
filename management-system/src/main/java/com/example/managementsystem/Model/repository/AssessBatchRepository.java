package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.AssessBatchPO;
import com.example.managementsystem.Model.tempQueryEntity.LongNumberEntity;
import com.example.managementsystem.Model.tempQueryEntity.SingleNumberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessBatchRepository extends CrudRepository<AssessBatchPO, Integer> {
    @Query("SELECT t FROM AssessBatchPO t WHERE t.groupId=:groupId AND t.year=:year AND t.quarter=:quarter")
    List<AssessBatchPO> getLatestAssessBatchByGroupId(@Param("groupId") int groupId, @Param("year") int year, @Param("quarter") int quarter);

    @Query("SELECT new SingleNumberEntity(max(t.year)) FROM AssessBatchPO t WHERE t.groupId=:groupId")
    List<SingleNumberEntity> getAssessBatchMaxYearByGroupId(@Param("groupId") int groupId);

    @Query("SELECT new SingleNumberEntity(max(t.quarter)) FROM AssessBatchPO t WHERE t.groupId=:groupId AND t.year=:year")
    List<SingleNumberEntity> getAssessBatchMaxQuarterByGroupId(@Param("groupId") int groupId, @Param("year") int year);

    @Query("SELECT t FROM AssessBatchPO t WHERE t.groupId=:groupId")
    List<AssessBatchPO> getAssessBatchByGroupId(@Param("groupId") int groupId, Pageable pageable);

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM AssessBatchPO t WHERE t.groupId=:groupId")
    List<LongNumberEntity> getAssessBatchAmountByGroupId(@Param("groupId") int groupId);

    @Query("SELECT t FROM AssessBatchPO t WHERE t.assess_batch_id=:assessBatchId")
    List<AssessBatchPO> getAssessBatchById(@Param("assessBatchId") int assessBatchId);

    @Query("SELECT t FROM AssessBatchPO t WHERE t.groupId=:groupId AND t.title=:title")
    List<AssessBatchPO> getAssessBatchBygGroupIdAndTitle(@Param("groupId") int groupId, @Param("title") String title, Pageable pageable);

    @Query("SELECT new LongNumberEntity(COUNT(t)) FROM AssessBatchPO t WHERE t.groupId=:groupId AND t.title=:title")
    List<LongNumberEntity> getAssessBatchAmountByGroupIdAndTitle(@Param("groupId") int groupId, @Param("title") String title);

    @Query("SELECT t FROM AssessBatchPO t WHERE t.groupId=:groupId AND t.title=:title")
    List<AssessBatchPO> getAssessBatchBygGroupIdAndTitle(@Param("groupId") int groupId, @Param("title") String title);

    @Query("SELECT t FROM AssessBatchPO t WHERE t.groupId=:groupId")
    List<AssessBatchPO> getTotalAssessBatchByGroupId(@Param("groupId") int groupId);
}
