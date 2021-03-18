package com.example.managementsystem.DAO;


import com.example.managementsystem.Model.SummaryPO;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SummaryDAO {
    int getSummaryAmountByUserId(int userId);

    int getSummaryAmountByUserIdAndQuarter(int userId,int year,int quarter);

    List<SummaryPO> getSummaryByUserId(int userId, int page, int limit);

    List<SummaryPO> getTotalSummaryByUserId(int userId);

    List<SummaryPO> getSummaryByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit);

    List<SummaryPO> getTotalSummaryByUserIdAndQuarter(int userId, int year, int quarter);

    List<SummaryPO> getSummaryById(int summaryId);

    void insertSummary(Map<String, Object> summaryInfo);

    void updateSummary(Map<String, Object> summaryInfo);

    void deleteSummaryById(int summaryId);

    List<QueryEntity> getSummaryByGroupIdAndUserName(String userName, int groupId);

    List<SummaryPO> getSummaryByUserIdAndTimeLimit(int userId, String beginTime, String endTime);
}
