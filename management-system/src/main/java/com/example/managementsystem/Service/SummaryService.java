package com.example.managementsystem.Service;

import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.DAO.OrganizationDAO;
import com.example.managementsystem.DAO.SummaryDAO;
import com.example.managementsystem.DAO.UserDAO;
import com.example.managementsystem.Model.*;
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
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.*;

@Service
public class SummaryService {
    @Autowired
    private SummaryDAO summaryDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public ResultInfo getSummaryByUserId(int userId, int page, int limit){
        List<SummaryPO> summaries = summaryDAO.getSummaryByUserId(userId, page, limit);
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> result = new ArrayList<>();
        for(int i = 0; i < summaries.size(); ++i){
            SummaryPO summary = summaries.get(i);
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(summary.getCreator()).get(0);
            temp.put("name", user.getName());
            temp.put("index", i + 1);
            temp.put("summary_id", summary.getSummary_id());
            temp.put("title", summary.getTitle());
            temp.put("submit_time", formatter.format(summary.getCreateTime()));
            temp.put("time", summary.getYear() + "年第" + summary.getQuarter() + "季度");
            result.add(temp);
        }
        res.put("count", summaryDAO.getSummaryAmountByUserId(userId));
        res.put("summarys", result);
        return new ResultInfo(200, "success", res);
    }

    public ResultInfo getSummaryByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit){
        List<SummaryPO> summaries = summaryDAO.getSummaryByUserIdAndQuarter(userId, year, quarter, page, limit);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        for(int i = 0; i < summaries.size(); ++i){
            SummaryPO summary = summaries.get(i);
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(summary.getCreator()).get(0);
            temp.put("name", user.getName());
            temp.put("summary_id", summary.getSummary_id());
            temp.put("title", summary.getTitle());
            temp.put("index", i + 1);
            result.add(temp);
        }
        res.put("count", summaryDAO.getSummaryAmountByUserIdAndQuarter(userId, year, quarter));
        res.put("summarys", result);
        return new ResultInfo(200, "success", res);
    }

    public ResultInfo getSummaryById(int summaryId){
        SummaryPO summary = summaryDAO.getSummaryById(summaryId).get(0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> result = new HashMap<>();
        UserPO user = userDAO.getUserById(summary.getCreator()).get(0);
        result.put("name", user.getName());
        result.put("summary_id", summary.getSummary_id());
        result.put("text", summary.getText());
        result.put("createTime", formatter.format(summary.getCreateTime()));
        result.put("title", summary.getTitle());
        return new ResultInfo(200,"success", result);
    }

    public ResultInfo newSummary(Map<String, Object> summaryInfo){
        summaryDAO.insertSummary(summaryInfo);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo updateSummary(Map<String, Object> summaryInfo){
        summaryDAO.updateSummary(summaryInfo);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getSummaryByGroupId(Map<String, Object> jsonParam){
        UserPO user = userDAO.getUserById((int)jsonParam.get("user_id")).get(0);
        int groupId = user.getAssessGroupId();
        AssessGroupPO group = assessGroupDAO.getGroupById(groupId).get(0);
        String[] memberList = group.getMemberList().split(",");
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> summaryList = new ArrayList<>();
        int index = 0;
        for(int i = 0;i < memberList.length; ++i){
            int userId = parseInt(memberList[i]);
            UserPO user2 = userDAO.getUserById(userId).get(0);
            List<SummaryPO> summaryPOS = summaryDAO.getTotalSummaryByUserId(userId);
            for(int j = 0;j < summaryPOS.size(); ++j) {
                Map<String, Object> temp = new HashMap<>();
                SummaryPO summary = summaryPOS.get(j);
                temp.put("summary_id", summary.getSummary_id());
                temp.put("title", summary.getTitle());
                temp.put("name", user2.getName());
                temp.put("index", ++index);
                summaryList.add(temp);
            }
        }
        int page = (int)jsonParam.get("page");
        int limit = (int)jsonParam.get("limit");
        result.put("count", index);
        result.put("summarys", summaryList.subList((page - 1) * limit, min(index, page * limit)));
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getSummaryByGroupIdAndUserName(Map<String, Object>jsonParam){
        UserPO user = userDAO.getUserById((int)jsonParam.get("user_id")).get(0);
        int groupId = user.getAssessGroupId();
        String userName = (String)jsonParam.get("name");
        List<QueryEntity> tempResult = summaryDAO.getSummaryByGroupIdAndUserName(userName, groupId);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = 0;i < tempResult.size(); ++i){
            QueryEntity queryEntity = tempResult.get(i);
            Map<String, Object> temp = new HashMap<>();
            temp.put("summary_id", queryEntity.getId());
            temp.put("title", queryEntity.getTitle());
            temp.put("name", queryEntity.getName());
            temp.put("index", ++index);
            tempList.add(temp);
        }
        result.put("count", index);
        result.put("summarys", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo deleteSummaryById(Map<String, Object>jsonParam){
        summaryDAO.deleteSummaryById((int)jsonParam.get("summary_id"));
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getSummaryByAdminUserId(int userId, int page, int limit){
        // 获取管辖内的所有userId
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserId(userId);

        // 根据userId 获取所有SummaryId
        List<Integer> summaryIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            List<SummaryPO> summarys = summaryDAO.getTotalSummaryByUserId(userIdList.get(i));
            for(int j = 0;j < summarys.size(); ++j){
                summaryIdList.add(summarys.get(j).getSummary_id());
            }
        }

        // summaryId 去重
        summaryIdList = summaryIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, summaryIdList.size()); ++i){
            SummaryPO summary = summaryDAO.getSummaryById(summaryIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(summary.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("summary_id", summary.getSummary_id());
            temp.put("title", summary.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", summaryIdList.size());
        result.put("summarys", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getSummaryByAdminUserIdAndName(int userId, String name, int page, int limit){
        // 获取管辖内的所有userId
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserId(userId);

        // 根据userId 和 name 获取所有SummaryId
        List<Integer> summaryIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
            if(user.getName().equals(name)){
                List<SummaryPO> summarys = summaryDAO.getTotalSummaryByUserId(userIdList.get(i));
                for(int j = 0;j < summarys.size(); ++j){
                    summaryIdList.add(summarys.get(j).getSummary_id());
                }
            }
        }

        // summaryId 去重
        summaryIdList = summaryIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, summaryIdList.size()); ++i){
            SummaryPO summary = summaryDAO.getSummaryById(summaryIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(summary.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("summary_id", summary.getSummary_id());
            temp.put("title", summary.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", summaryIdList.size());
        result.put("summarys", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getSummaryByAdminUserIdAndOrganizationId(int userId, int organizationId, int isOrganization, int page, int limit){
        // 判断权限
        Boolean permission = userDAO.judgePermissionOnAnOrganization(userId, organizationId, isOrganization);
        if(permission == false){
            return new ResultInfo(0, "没有该机构权限", new HashMap<>());
        }

        // 获取该Organization管辖到的用户
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(userId, organizationId, isOrganization);

        // 获取summaryIdList
        List<Integer> summaryIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
            List<SummaryPO> summarys = summaryDAO.getTotalSummaryByUserId(userIdList.get(i));
            for(int j = 0;j < summarys.size(); ++j) {
                summaryIdList.add(summarys.get(j).getSummary_id());
            }
        }
        // 对summaryId 去重
        summaryIdList = summaryIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, summaryIdList.size()); ++i){
            SummaryPO summary = summaryDAO.getSummaryById(summaryIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(summary.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("summary_id", summary.getSummary_id());
            temp.put("title", summary.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", summaryIdList.size());
        result.put("summarys", tempList);
        return new ResultInfo(200, "success", result);

    }

    public ResultInfo getSummaryByUserIdAndTimeLimit(int userId, String timeLimit, int page, int limit) {
        String[] time = timeLimit.split("~");
        String beginTimeStr = time[0].strip() + "-01";
        String[] endTimeList = time[1].strip().split("-");
        String endTimeStr = endTimeList[0] + "-" + endTimeList[1] + "-" + TimeTools.getLastDayOfMonth(parseInt(endTimeList[0]), parseInt(endTimeList[1]));

        List<SummaryPO> summarys = summaryDAO.getSummaryByUserIdAndTimeLimit(userId, beginTimeStr, endTimeStr);

        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, summarys.size()); ++i){
            SummaryPO summary = summarys.get(i);
            UserPO thisUser = userDAO.getUserById(summary.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("summary_id", summary.getSummary_id());
            temp.put("title", summary.getTitle());
            temp.put("name", thisUser.getName());
            temp.put("time", summary.getYear() + "年第" + summary.getQuarter() + "季度");
            temp.put("submit_time", formatter.format(summary.getCreateTime()));
            tempList.add(temp);
        }
        result.put("count", summarys.size());
        result.put("summarys", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo exportSummaryExcelFile(String name, int organizationId, int isOrganization, String timeLimit, HttpServletResponse response){
        String[] time = timeLimit.split("~");
        String beginTimeStr = time[0].strip() + "-01";
        String[] endTimeList = time[1].strip().split("-");
        String endTimeStr = endTimeList[0] + "-" + endTimeList[1] + "-" + TimeTools.getLastDayOfMonth(parseInt(endTimeList[0]), parseInt(endTimeList[1]));
        List<Integer> summaryIdList = new ArrayList<>();
        if(name.equals("")){
            List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(1, organizationId, isOrganization);
            // 获取summaryIdList
            for(int i = 0;i < userIdList.size(); ++i){
                List<SummaryPO> summarys = summaryDAO.getSummaryByUserIdAndTimeLimit(userIdList.get(i), beginTimeStr, endTimeStr);
                for(int j = 0;j < summarys.size(); ++j) {
                    summaryIdList.add(summarys.get(j).getSummary_id());
                }
            }
            // 对 summaryIdList 去重
            summaryIdList = summaryIdList.stream().distinct().collect(Collectors.toList());
        } else {
            List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(1, organizationId, isOrganization);
            // 获取summaryIdList
            for(int i = 0;i < userIdList.size(); ++i){
                UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
                if(!user.getName().equals(name))continue;
                List<SummaryPO> summarys = summaryDAO.getSummaryByUserIdAndTimeLimit(userIdList.get(i), beginTimeStr, endTimeStr);
                for(int j = 0;j < summarys.size(); ++j) {
                    summaryIdList.add(summarys.get(j).getSummary_id());
                }
            }
            // 对 summaryIdList 去重
            summaryIdList = summaryIdList.stream().distinct().collect(Collectors.toList());
        }
        List<List<String>> excelInfo = new ArrayList<>();
        List<String> title = new ArrayList<>();
        title.add("总结标题");title.add("姓名");title.add("年份");title.add("季度");title.add("创建时间");title.add("最后更新时间");title.add("总结内容");
        title.add("电话号码");title.add("所属单位");title.add("考核单位");title.add("考核组");
        for(int i = 0;i < summaryIdList.size(); ++i){
            SummaryPO summary = summaryDAO.getSummaryById(summaryIdList.get(i)).get(0);
            List<String> temp = new ArrayList<>();
            temp.add(summary.getTitle());
            UserPO user = userDAO.getUserById(summary.getCreator()).get(0);
            temp.add(user.getName());
            temp.add(String.valueOf(summary.getYear()));
            temp.add(String.valueOf(summary.getQuarter()));
            temp.add(formatter.format(summary.getCreateTime()));
            temp.add(formatter.format(summary.getUpdateTime()));
            temp.add(summary.getText());
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

        String fileName = "";

        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            fileName = organization.getName() + timeLimit + "总结";
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            fileName = assessGroup.getName() + timeLimit + "总结";
        }

        XSSFWorkbook wb = ExcelUtil.getXSSFWorkbook("总结表", title, excelInfo);

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
