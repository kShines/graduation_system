package com.example.managementsystem.DAO;

import com.example.managementsystem.Model.AssessBatchPO;
import com.example.managementsystem.Model.tempQueryEntity.SingleNumberEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessBatchDAO {

    List<AssessBatchPO> getLatestAssessBatchByGroupId(int groupId);

    int getAssessBatchMaxYearByGroupId(int groupId);

    int getAssessBatchMaxQuarterByGroupId(int groupId, int year);

    List<AssessBatchPO> getAssessBatchByGroupId(int groupId, int page, int limit);

    int getAssessBatchAmountByGroupId(int groupId);

    List<AssessBatchPO> getAssessBatchById(int assessBatchId);

    void createAssessBatch(AssessBatchPO assessBatch);

    void updateAssessBatch(AssessBatchPO assessBatch);

    void deleteAssessBatchById(int assessBatchId);

    List<AssessBatchPO> getAssessBatchByGroupIdAndTitle(int groupId, String title, int page, int limit);

    int getAssessBatchAmountByGroupIdAndTitle(int groupId, String title);

    List<AssessBatchPO> getAssessBatchByGroupIdAndTitle(int groupId, String title);

    List<AssessBatchPO> getTotalAssessBatchByGroupId(int groupId);
}
