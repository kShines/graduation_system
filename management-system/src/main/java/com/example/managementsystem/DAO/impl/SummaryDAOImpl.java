package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.SummaryDAO;
import com.example.managementsystem.Model.SummaryPO;
import com.example.managementsystem.Model.repository.SummaryRepository;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class SummaryDAOImpl implements SummaryDAO {

    @Autowired
    private SummaryRepository summaryRepository;

    public int getSummaryAmountByUserId(int userId) {
        return summaryRepository.getSummaryAmountByUserId(userId).get(0).getNumber().intValue();
    }

    public int getSummaryAmountByUserIdAndQuarter(int userId, int year, int quarter) {
        return summaryRepository.getSummaryAmountByUserIdAndQuarter(userId, year, quarter).get(0).getNumber().intValue();
    }

    public List<SummaryPO> getSummaryByUserId(int userId, int page, int limit) {
        return summaryRepository.getSummaryByUserId(userId, PageRequest.of(page,limit)).getContent();
    }

    public List<SummaryPO> getTotalSummaryByUserId(int userId) {
        return summaryRepository.getTotalSummaryByUserId(userId);
    }

    public List<SummaryPO> getSummaryByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit) {
        return summaryRepository.getSummaryByUserIdAndQuarter(userId, year, quarter, PageRequest.of(page, limit)).getContent();
    }

    public List<SummaryPO> getTotalSummaryByUserIdAndQuarter(int userId, int year, int quarter) {
        return summaryRepository.getTotalSummaryByUserIdAndQuarter(userId, year, quarter);
    }

    public List<SummaryPO> getSummaryById(int summaryId) {
        return summaryRepository.getSummaryById(summaryId);
    }

    public void insertSummary(Map<String, Object> summaryInfo) {
        SummaryPO summary = new SummaryPO();
        summary.setCreator((int)summaryInfo.get("user_id"));
        summary.setQuarter((int)summaryInfo.get("quarter"));
        summary.setText((String)summaryInfo.get("text"));
        summary.setTitle((String)summaryInfo.get("title"));
        summary.setYear((int)summaryInfo.get("year"));
        summary.setCreateTime(new Date(System.currentTimeMillis()));
        summary.setUpdateTime(new Date(System.currentTimeMillis()));
        summaryRepository.save(summary);
    }

    public void updateSummary(Map<String, Object> summaryInfo) {
        int summaryId = (int)summaryInfo.get("summary_id");
        List<SummaryPO> summaryPO = summaryRepository.getSummaryById(summaryId);
        SummaryPO summary = summaryPO.get(0);
        summary.setQuarter((int)summaryInfo.get("quarter"));
        summary.setText((String)summaryInfo.get("text"));
        summary.setTitle((String)summaryInfo.get("title"));
        summary.setYear((int)summaryInfo.get("year"));
        summary.setUpdateTime(new Date(System.currentTimeMillis()));
        summaryRepository.save(summary);
    }

    public void deleteSummaryById(int summaryId) {
        summaryRepository.deleteById(summaryId);
    }

    public List<QueryEntity> getSummaryByGroupIdAndUserName(String userName, int groupId) {
        return summaryRepository.getSummaryByGroupIdAndUserName(userName, groupId);
    }

    public List<SummaryPO> getSummaryByUserIdAndTimeLimit(int userId, String beginTime, String endTime) {
        return summaryRepository.getSummaryByUserIdAndTimeLimit(userId, beginTime, endTime);
    }
}
