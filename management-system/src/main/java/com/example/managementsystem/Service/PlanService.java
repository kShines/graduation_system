package com.example.managementsystem.Service;

import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.DAO.OrganizationDAO;
import com.example.managementsystem.DAO.PlanDAO;
import com.example.managementsystem.DAO.UserDAO;
import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.OrganizationPO;
import com.example.managementsystem.Model.PlanPO;
import com.example.managementsystem.Model.UserPO;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import com.example.managementsystem.util.ExcelUtil;
import com.example.managementsystem.util.ResultInfo;
import com.example.managementsystem.util.TimeTools;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;

@Service
public class PlanService {
    @Autowired
    private PlanDAO planDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private UserService userService;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public ResultInfo getPlansByUserId(int userId, int limit, int page){
        List<PlanPO> plans = planDAO.getPlansByUserId(userId, limit, page);
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>>result = new ArrayList<>();
        for(int i = 0;i < plans.size(); ++i){
            PlanPO plan = plans.get(i);
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(plan.getCreator()).get(0);
            temp.put("name", user.getName());
            temp.put("index", i + 1);
            temp.put("plan_id", plan.getPlan_id());
            temp.put("time", plan.getYear() + "年第" + plan.getQuarter() + "季度");
            temp.put("submit_time", formatter.format(plan.getCreateTime()));
            temp.put("title", plan.getTitle());
            result.add(temp);
        }
        res.put("plans", result);
        res.put("count", planDAO.getPlanAmountByUserId(userId));
        return new ResultInfo(0, "success", res);
    }

    public ResultInfo getPlansByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit){
        List<PlanPO> plans = planDAO.getPlanByUserIdAndQuarter(userId, year, quarter, page, limit);
        List<Map<String, Object>>result = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        for(int i = 0;i < plans.size(); ++i){
            PlanPO plan = plans.get(i);
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(plan.getCreator()).get(0);
            temp.put("name", user.getName());
            temp.put("index", i + 1);
            temp.put("plan_id", plan.getPlan_id());
            temp.put("title", plan.getTitle());
            result.add(temp);
        }
        res.put("count",planDAO.getPlanAmountByUserIdAndQuarter(userId, year, quarter));
        res.put("plans", result);
        return new ResultInfo(0, "success", res);
    }

    public ResultInfo getPlanById(int planId){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        PlanPO plan = planDAO.getPlanById(planId).get(0);
        Map<String, Object> result = new HashMap<>();
        UserPO user = userDAO.getUserById(plan.getCreator()).get(0);
        result.put("name", user.getName());
        result.put("plan_id", plan.getPlan_id());
        result.put("text", plan.getText());
        result.put("createTime", formatter.format(plan.getCreateTime()));
        result.put("title", plan.getTitle());
        return new ResultInfo(0, "success", result);
    }

    public ResultInfo newPlan(Map<String, Object> planInfo){
        planDAO.insertPlan(planInfo);
        return new ResultInfo(0, "success", new HashMap<>());
    }

    public ResultInfo updatePlanById(Map<String, Object> planInfo){
        planDAO.updatePlan(planInfo);
        return new ResultInfo(0, "success", new HashMap<>());
    }

    public ResultInfo getPlanByGroupId(Map<String, Object> jsonParam){
        UserPO user = userDAO.getUserById((int)jsonParam.get("user_id")).get(0);
        int groupId = user.getAssessGroupId();
        AssessGroupPO group = assessGroupDAO.getGroupById(groupId).get(0);
        String[] memberList = group.getMemberList().split(",");
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> planList = new ArrayList<>();
        int index = 0;
        for(int i = 0;i < memberList.length; ++i){
            int userId = parseInt(memberList[i]);
            UserPO user2 = userDAO.getUserById(userId).get(0);
            List<PlanPO> plans = planDAO.getTotalPlanByUserId(userId);
            for(int j = 0;j < plans.size(); ++j) {
                Map<String, Object> temp = new HashMap<>();
                PlanPO plan = plans.get(j);
                temp.put("plan_id", plan.getPlan_id());
                temp.put("title", plan.getTitle());
                temp.put("name", user2.getName());
                temp.put("index", ++index);
                planList.add(temp);
            }
        }
        int page = (int)jsonParam.get("page");
        int limit = (int)jsonParam.get("limit");
        result.put("plans", planList.subList((page - 1) * limit, min(index, page * limit)));
        result.put("count", index);
        return new ResultInfo(0, "success", result);
    }

    public ResultInfo getPlanByGroupIdAndUserName(Map<String, Object> jsonParam){
        UserPO user = userDAO.getUserById((int)jsonParam.get("user_id")).get(0);
        int groupId = user.getAssessGroupId();
        String userName = (String)jsonParam.get("name");
        List<QueryEntity> tempResult = planDAO.getPlanByGroupIdAndUserName(userName, groupId);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = 0;i < tempResult.size(); ++i){
            QueryEntity queryEntity = tempResult.get(i);
            Map<String, Object> temp = new HashMap<>();
            temp.put("plan_id", queryEntity.getId());
            temp.put("title", queryEntity.getTitle());
            temp.put("name", queryEntity.getName());
            temp.put("index", ++index);
            tempList.add(temp);
        }
        result.put("count", index);
        result.put("plans", tempList);
        return new ResultInfo(0, "success", result);
    }

    public ResultInfo deletePlanById(int planId){
        planDAO.deletePlanById(planId);
        return new ResultInfo(0, "success", new HashMap<>());
    }

    public ResultInfo getPlanByAdminUserId(int userId, int page, int limit){
        // 获取管辖内的所有userId
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserId(userId);

        // 根据userId 获取每个人的planId
        List<Integer> planIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            List<PlanPO> plans = planDAO.getTotalPlanByUserId(userIdList.get(i));
            for(int j = 0;j < plans.size(); ++j){
                planIdList.add(plans.get(j).getPlan_id());
            }
        }
        // 对planId 去重
        planIdList = planIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, planIdList.size()); ++i){
            PlanPO plan = planDAO.getPlanById(planIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(plan.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("plan_id", plan.getPlan_id());
            temp.put("title", plan.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", planIdList.size());
        result.put("plans", tempList);
        return new ResultInfo(200, "success", result);

    }

    public ResultInfo getPlanByAdminUserIdAndName(int userId, String name, int page, int limit){
        // 获取管辖内的所有userId
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserId(userId);

        // 根据userId 和 name 获取PlanId
        List<Integer> planIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
            if(user.getName().equals(name)){
                List<PlanPO> plans = planDAO.getTotalPlanByUserId(userIdList.get(i));
                for(int j = 0;j < plans.size(); ++j){
                    planIdList.add(plans.get(j).getPlan_id());
                }
            }
        }

        // 对planId 去重
        planIdList = planIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, planIdList.size()); ++i){
            PlanPO plan = planDAO.getPlanById(planIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(plan.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("plan_id", plan.getPlan_id());
            temp.put("title", plan.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", planIdList.size());
        result.put("plans", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getPlanByAdminUserIdAndOrganizationId(int userId, int organizationId, int isOrganization, int page, int limit){
        // 获取该Organization管辖到的用户
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(userId, organizationId, isOrganization);

        // 获取planIdList
        List<Integer> planIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            List<PlanPO> plans = planDAO.getTotalPlanByUserId(userIdList.get(i));
            for(int j = 0;j < plans.size(); ++j) {
                planIdList.add(plans.get(j).getPlan_id());
            }
        }
        // 对planId 去重
        planIdList = planIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, planIdList.size()); ++i){
            PlanPO plan = planDAO.getPlanById(planIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(plan.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("plan_id", plan.getPlan_id());
            temp.put("title", plan.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", planIdList.size());
        result.put("plans", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getPlanByUserIdAndTimeLimit(int userId, String timeLimit, int page, int limit){
        String[] time = timeLimit.split("~");
        String beginTimeStr = time[0].strip() + "-01";
        String[] endTimeList = time[1].strip().split("-");
        String endTimeStr = endTimeList[0] + "-" + endTimeList[1] + "-" + TimeTools.getLastDayOfMonth(parseInt(endTimeList[0]), parseInt(endTimeList[1]));
        List<PlanPO> plans = planDAO.getPlanByUserIdAndTimeLimit(userId, beginTimeStr, endTimeStr);
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit; i < min(page * limit, plans.size()); ++i){
            PlanPO plan = plans.get(i);
            UserPO thisUser = userDAO.getUserById(plan.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("plan_id", plan.getPlan_id());
            temp.put("title", plan.getTitle());
            temp.put("submit_time", formatter.format(plan.getCreateTime()));
            temp.put("time", plan.getYear() + "年第" + plan.getQuarter() + "季度");
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", plans.size());
        result.put("plans", tempList);
        return new ResultInfo(200,"success", result);
    }

    public ResultInfo exportPlanExcelFile(String name, int organizationId, int isOrganization, String timeLimit, HttpServletResponse response){
        String[] time = timeLimit.split("~");
        String beginTimeStr = time[0].strip() + "-01";
        String[] endTimeList = time[1].strip().split("-");
        String endTimeStr = endTimeList[0] + "-" + endTimeList[1] + "-" + TimeTools.getLastDayOfMonth(parseInt(endTimeList[0]), parseInt(endTimeList[1]));
        List<Integer> planIdList = new ArrayList<>();
        if(name.equals("")){
            List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(1, organizationId, isOrganization);
            // 获取planIdList
            for(int i = 0;i < userIdList.size(); ++i){
                List<PlanPO> plans = planDAO.getPlanByUserIdAndTimeLimit(userIdList.get(i), beginTimeStr, endTimeStr);
                for(int j = 0;j < plans.size(); ++j) {
                    planIdList.add(plans.get(j).getPlan_id());
                }
            }
            // 对planId 去重
            planIdList = planIdList.stream().distinct().collect(Collectors.toList());
        } else {
            List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(1, organizationId, isOrganization);
            // 获取planIdList
            for(int i = 0;i < userIdList.size(); ++i){
                UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
                if(!user.getName().equals(name))continue;
                List<PlanPO> plans = planDAO.getPlanByUserIdAndTimeLimit(userIdList.get(i), beginTimeStr, endTimeStr);
                for(int j = 0;j < plans.size(); ++j) {
                    planIdList.add(plans.get(j).getPlan_id());
                }
            }
            // 对planId 去重
            planIdList = planIdList.stream().distinct().collect(Collectors.toList());
        }
        List<List<String>> excelInfo = new ArrayList<>();
        List<String> title = new ArrayList<>();
        title.add("计划标题");title.add("姓名");title.add("年份");title.add("季度");title.add("创建时间");title.add("最后更新时间");title.add("计划内容");
        title.add("电话号码");title.add("所属单位");title.add("考核单位");title.add("考核组");
        for(int i = 0;i < planIdList.size(); ++i){
            PlanPO plan = planDAO.getPlanById(planIdList.get(i)).get(0);
            List<String> temp = new ArrayList<>();
            temp.add(plan.getTitle());
            UserPO user = userDAO.getUserById(plan.getCreator()).get(0);
            temp.add(user.getName());
            temp.add(String.valueOf(plan.getYear()));
            temp.add(String.valueOf(plan.getQuarter()));
            temp.add(formatter.format(plan.getCreateTime()));
            temp.add(formatter.format(plan.getUpdateTime()));
            temp.add(plan.getText());
            temp.add(user.getUsername());
            if(user.getOrganization() != null){
                OrganizationPO organization = organizationDAO.getOrganizationById(user.getOrganization()).get(0);
                temp.add(organization.getName());
            } else {
                temp.add("");
            }
            if(user.getAssessOrganizationId() != null){
                OrganizationPO assessOrganization = organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0);
                temp.add(assessOrganization.getName());
            } else {
                temp.add("");
            }
            if(user.getAssessGroupId() != null){
                AssessGroupPO assessGroup = assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0);
                temp.add(assessGroup.getName());
            } else {
                temp.add("");
            }
            excelInfo.add(temp);
        }
        String fileName = "计划表";
        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            fileName = organization.getName() + timeLimit + "计划";
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            fileName = assessGroup.getName() + timeLimit + "计划";
        }

        XSSFWorkbook wb = ExcelUtil.getXSSFWorkbook("计划表", title, excelInfo);

        try {
            fileName = new String(fileName.getBytes(), "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"),"ISO8859-1") + ".xls");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultInfo(200, "success", new HashMap<>());
    }
}
