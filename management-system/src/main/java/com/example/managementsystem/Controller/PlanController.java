package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.PlanService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class PlanController {

    @Autowired
    private PlanService planService;

    @RequestMapping(value = "getAllPlans", method = RequestMethod.POST)
    public ResultInfo getAllPlans(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlansByUserId(
                 (int)jsonParam.get("user_id")
                ,(int)jsonParam.get("limit")
                ,(int)jsonParam.get("page") - 1
        );
    }

    @RequestMapping(value = "getPlansByQuarter", method = RequestMethod.POST)
    public ResultInfo getPlansByQuarter(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlansByUserIdAndQuarter(
                (int)jsonParam.get("user_id")
                ,(int)jsonParam.get("year")
                ,(int)jsonParam.get("quarter")
                ,(int)jsonParam.get("page") - 1
                ,(int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value = "getPlanById", method = RequestMethod.POST)
    public ResultInfo getPlanById(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlanById((int)jsonParam.get("plan_id"));
    }

    @RequestMapping(value = "createPlan", method = RequestMethod.POST)
    public ResultInfo createPlan(@RequestBody Map<String, Object> jsonParam){
        return planService.newPlan(jsonParam);
    }

    @RequestMapping(value="updatePlanById", method = RequestMethod.POST)
    public ResultInfo updatePlanById(@RequestBody Map<String, Object> jsonParam){
        return planService.updatePlanById(jsonParam);
    }

    @RequestMapping(value="getPlanByGroupId", method = RequestMethod.POST)
    public ResultInfo getPlanByGroupId(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlanByGroupId(jsonParam);
    }

    @RequestMapping(value="getPlanByName", method = RequestMethod.POST)
    public ResultInfo getPlanByName(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlanByGroupIdAndUserName(jsonParam);
    }

    @RequestMapping(value="deletePlanById", method = RequestMethod.POST)
    public ResultInfo deletePlanById(@RequestBody Map<String, Object> jsonParam){
        return planService.deletePlanById((int)jsonParam.get("plan_id"));
    }

    @RequestMapping(value="getPlanByAdminUserId", method = RequestMethod.POST)
    public ResultInfo getPlanByAdminUserId(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlanByAdminUserId(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getPlanByAdminUserIdAndName", method = RequestMethod.POST)
    public ResultInfo getPlanByAdminUserIdAndName(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlanByAdminUserIdAndName(
                (int)jsonParam.get("user_id"),
                (String)jsonParam.get("name"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getPlanByAdminUserIdAndOrganizationId", method = RequestMethod.POST)
    public ResultInfo getPlanByAdminUserIdAndOrganizationId(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlanByAdminUserIdAndOrganizationId(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getPlanByUserIdAndTimeLimit", method = RequestMethod.POST)
    public ResultInfo getPlanByUserIdAndTimeLimit(@RequestBody Map<String, Object> jsonParam){
        return planService.getPlanByUserIdAndTimeLimit(
                (int)jsonParam.get("user_id"),
                (String)jsonParam.get("time_limit"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="exportPlanExcelFile", method = RequestMethod.GET, produces="application/octet-stream")
    public void exportPlanExcelFile(String name, int organization_id, int is_organization, String time_limit, HttpServletResponse response){
        planService.exportPlanExcelFile(
                name,
                organization_id,
                is_organization,
                time_limit,
                response
        );
    }
}
