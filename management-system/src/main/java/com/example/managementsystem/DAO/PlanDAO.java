package com.example.managementsystem.DAO;

import com.example.managementsystem.Model.PlanPO;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PlanDAO {

    int getPlanAmountByUserId(int userId);

    int getPlanAmountByUserIdAndQuarter(int userId, int year, int quarter);

    List<PlanPO> getTotalPlanByUserId(int userId);

    List<PlanPO> getPlansByUserId(int userId, int limit, int page);

    List<PlanPO> getPlanByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit);

    List<PlanPO> getTotalPlanByUserIdAndQuarter(int userId, int year, int quarter);

    List<PlanPO> getPlanById(int planId);

    void insertPlan(Map<String, Object> planInfo);

    void updatePlan(Map<String, Object> planInfo);

    void deletePlanById(int planId);

    List<QueryEntity> getPlanByGroupIdAndUserName(String userName, int groupId);

    List<PlanPO> getPlanByUserIdAndTimeLimit(int userId, String beginTime, String endTime);
}
