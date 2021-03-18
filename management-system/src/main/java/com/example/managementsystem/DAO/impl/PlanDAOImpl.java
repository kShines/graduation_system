package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.PlanDAO;
import com.example.managementsystem.Model.PlanPO;
import com.example.managementsystem.Model.repository.PlanRepository;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class PlanDAOImpl implements PlanDAO {

    @Autowired
    private PlanRepository planRepository;


    public int getPlanAmountByUserId(int userId) {
        return planRepository.getPlanAmountByUserId(userId).get(0).getNumber().intValue();
    }

    public int getPlanAmountByUserIdAndQuarter(int userId, int year, int quarter) {
        return planRepository.getPlanAmountByUserIdAndQuarter(userId, year, quarter).get(0).getNumber().intValue();
    }

    public List<PlanPO> getTotalPlanByUserId(int userId) {
        return planRepository.getTotalPlanByUserId(userId);
    }

    public List<PlanPO> getPlansByUserId(int userId, int limit, int page) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return planRepository.getPlansByUserId(userId, pageRequest).getContent();
    }

    public List<PlanPO> getPlanByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit) {
        return planRepository.getPlansByUserIdAndQuarter(userId, year, quarter, PageRequest.of(page, limit)).getContent();
    }

    public List<PlanPO> getTotalPlanByUserIdAndQuarter(int userId, int year, int quarter) {
        return planRepository.getTotalPlanByUserIdAndQuarter(userId, year, quarter);
    }

    public List<PlanPO> getPlanById(int planId) {
        return planRepository.getPlanById(planId);
    }

    public void insertPlan(Map<String, Object> planInfo){
        PlanPO plan = new PlanPO();
        plan.setCreator((int)planInfo.get("user_id"));
        plan.setQuarter((int)planInfo.get("quarter"));
        plan.setText((String)planInfo.get("text"));
        plan.setYear((int)planInfo.get("year"));
        plan.setTitle((String)planInfo.get("title"));
        plan.setUpdateTime(new Date(System.currentTimeMillis()));
        plan.setCreateTime(new Date(System.currentTimeMillis()));
        planRepository.save(plan);
    }

    public void updatePlan(Map<String, Object> planInfo) {
        int planId = (int)planInfo.get("plan_id");
        List<PlanPO> plans = getPlanById(planId);
        PlanPO plan = plans.get(0);
        plan.setUpdateTime(new Date(System.currentTimeMillis()));
        // plan.setQuarter((int)planInfo.get("quarter"));
        plan.setText((String)planInfo.get("text"));
        // plan.setYear((int)planInfo.get("year"));
        plan.setTitle((String)planInfo.get("title"));
        planRepository.save(plan);
    }

    public void deletePlanById(int planId) {
        planRepository.deleteById(planId);
    }

    public List<QueryEntity> getPlanByGroupIdAndUserName(String userName, int groupId) {
        return planRepository.getPlanByName(userName, groupId);
    }

    public List<PlanPO> getPlanByUserIdAndTimeLimit(int userId, String beginTime, String endTime) {
        return planRepository.getPlanByUserIdAndTimeLimit(userId, beginTime, endTime);
    }

}
