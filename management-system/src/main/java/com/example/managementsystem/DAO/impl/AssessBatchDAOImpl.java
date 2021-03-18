package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.AssessBatchDAO;
import com.example.managementsystem.Model.AssessBatchPO;
import com.example.managementsystem.Model.repository.AssessBatchRepository;
import com.example.managementsystem.Model.tempQueryEntity.SingleNumberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
import java.util.List;

@Component
public class AssessBatchDAOImpl implements AssessBatchDAO {
    @Autowired
    private AssessBatchRepository assessBatchRepository;


    public List<AssessBatchPO> getLatestAssessBatchByGroupId(int groupId) {
        int maxYear = getAssessBatchMaxYearByGroupId(groupId);
        int maxQuarter = getAssessBatchMaxQuarterByGroupId(groupId, maxYear);
        return assessBatchRepository.getLatestAssessBatchByGroupId(groupId, maxYear, maxQuarter);
    }

    public int getAssessBatchMaxYearByGroupId(int groupId) {
        List<SingleNumberEntity> year = assessBatchRepository.getAssessBatchMaxYearByGroupId(groupId);
        return year.get(0).getNumber();
    }

    public int getAssessBatchMaxQuarterByGroupId(int groupId, int year) {
        List<SingleNumberEntity> quarter = assessBatchRepository.getAssessBatchMaxQuarterByGroupId(groupId, year);
        return quarter.get(0).getNumber();
    }

    public List<AssessBatchPO> getAssessBatchByGroupId(int groupId, int page, int limit) {
        return assessBatchRepository.getAssessBatchByGroupId(groupId, PageRequest.of(page, limit));
    }

    public int getAssessBatchAmountByGroupId(int groupId) {
        return assessBatchRepository.getAssessBatchAmountByGroupId(groupId).get(0).getNumber().intValue();
    }

    public List<AssessBatchPO> getAssessBatchById(int assessBatchId) {
        return assessBatchRepository.getAssessBatchById(assessBatchId);
    }

    public void createAssessBatch(AssessBatchPO assessBatch) {
        assessBatchRepository.save(assessBatch);
    }

    public void updateAssessBatch(AssessBatchPO assessBatch) {
        assessBatchRepository.save(assessBatch);
    }

    public void deleteAssessBatchById(int assessBatchId) {
        assessBatchRepository.deleteById(assessBatchId);
    }

    public List<AssessBatchPO> getAssessBatchByGroupIdAndTitle(int groupId, String title, int page, int limit) {
        return assessBatchRepository.getAssessBatchBygGroupIdAndTitle(groupId, title, PageRequest.of(page, limit));
    }

    public int getAssessBatchAmountByGroupIdAndTitle(int groupId, String title) {
        return assessBatchRepository.getAssessBatchAmountByGroupIdAndTitle(groupId, title).get(0).getNumber().intValue();
    }

    public List<AssessBatchPO> getAssessBatchByGroupIdAndTitle(int groupId, String title) {
        return null;
    }

    public List<AssessBatchPO> getTotalAssessBatchByGroupId(int groupId) {
        return assessBatchRepository.getTotalAssessBatchByGroupId(groupId);
    }


}
