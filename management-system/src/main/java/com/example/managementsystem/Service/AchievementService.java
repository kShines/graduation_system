package com.example.managementsystem.Service;

import com.example.managementsystem.DAO.AchievementDAO;
import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.DAO.OrganizationDAO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;

@Service
public class AchievementService {

    @Autowired
    private AchievementDAO achievementDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public ResultInfo getAllAchievementsByUserId(int userId, int page, int limit){
        List<AchievementPO> achievements = achievementDAO.getAchievementByUserId(userId, page, limit);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        for(int i = 0;i < achievements.size(); ++i){
            AchievementPO achievement = achievements.get(i);
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(achievement.getCreator()).get(0);
            temp.put("name", user.getName());
            temp.put("index", i + 1);
            temp.put("achievement_id", achievement.getAchievement_id());
            temp.put("title", achievement.getTitle());
            temp.put("submit_time", formatter.format(achievement.getCreateTime()));
            temp.put("time", achievement.getYear() + "年第" + achievement.getQuarter() + "季度");
            result.add(temp);
        }
        res.put("count", achievementDAO.getAchievementAmountByUserId(userId));
        res.put("achievements", result);
        return new ResultInfo(200, "success", res);
    }

    public ResultInfo getAchievementsByUserIdAndQuarter(int userId, int year, int quarter, int page, int limit){
        List<AchievementPO> achievements = achievementDAO.getAchievementsByUserIdAndQuarter(userId, year, quarter, page, limit);
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        for(int i = 0;i < achievements.size(); ++i){
            AchievementPO achievement = achievements.get(i);
            Map<String, Object> temp = new HashMap<>();
            UserPO user = userDAO.getUserById(achievement.getCreator()).get(0);
            temp.put("name", user.getName());
            temp.put("achievement_id", achievement.getAchievement_id());
            temp.put("title", achievement.getTitle());
            temp.put("index", i + 1);
            result.add(temp);
        }
        res.put("count", achievementDAO.getAchievementAmountByUserIdAndQuarter(userId, year, quarter));
        res.put("achievements", result);
        return new ResultInfo(200, "success", res);
    }

    public ResultInfo getAchievementById(int achievementId){
        AchievementPO achievement = achievementDAO.getAchievementById(achievementId).get(0);
        Map<String, Object> result = new HashMap<>();
        UserPO user = userDAO.getUserById(achievement.getCreator()).get(0);
        result.put("name", user.getName());
        result.put("achievement_id", achievement.getAchievement_id());
        result.put("text_1", achievement.getText_1());
        result.put("text_2", achievement.getText_2());
        result.put("text_3", achievement.getText_3());
        result.put("createTime", formatter.format(achievement.getCreateTime()));
        result.put("title", achievement.getTitle());
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo newAchievement(Map<String, Object> achievementInfo){
        achievementDAO.insertAchievement(achievementInfo);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo updateAchievement(Map<String, Object> achievementInfo){
        achievementDAO.updateAchievement(achievementInfo);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getAchievementByGroupId(Map<String, Object>jsonParam){
        UserPO user = userDAO.getUserById((int)jsonParam.get("user_id")).get(0);
        int groupId = user.getAssessGroupId();
        AssessGroupPO group = assessGroupDAO.getGroupById(groupId).get(0);
        String[] memberList = group.getMemberList().split(",");
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> achievementList = new ArrayList<>();
        int index = 0;
        for(int i = 0;i < memberList.length; ++i){
            int userId = parseInt(memberList[i]);
            UserPO user2 = userDAO.getUserById(userId).get(0);
            List<AchievementPO> achievements = achievementDAO.getTotalAchievementByUserId(userId);
            for(int j = 0;j < achievements.size(); ++j) {
                Map<String, Object> temp = new HashMap<>();
                AchievementPO achievement = achievements.get(j);
                temp.put("achievement_id", achievement.getAchievement_id());
                temp.put("title", achievement.getTitle());
                temp.put("name", user2.getName());
                temp.put("index", ++index);
                achievementList.add(temp);
            }
        }
        int page = (int)jsonParam.get("page");
        int limit = (int)jsonParam.get("limit");
        result.put("count", index);
        result.put("achievements", achievementList.subList((page - 1) * limit, min(page * limit, index)));
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAchievementByGroupIdAndUserName(Map<String, Object>jsonParam){
        UserPO user = userDAO.getUserById((int)jsonParam.get("user_id")).get(0);
        int groupId = user.getAssessGroupId();
        String userName = (String)jsonParam.get("name");
        List<QueryEntity> tempResult = achievementDAO.getAchievementByGroupIdAndUserName(userName, groupId);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = 0;i < tempResult.size(); ++i) {
            QueryEntity queryEntity = tempResult.get(i);
            Map<String, Object> temp = new HashMap<>();
            temp.put("achievement_id", queryEntity.getId());
            temp.put("title", queryEntity.getTitle());
            temp.put("name", queryEntity.getName());
            temp.put("index", ++index);
            tempList.add(temp);
        }
        result.put("count", index);
        result.put("achievements", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo deleteAchievementById(int achievementId){
        achievementDAO.deleteAchievementById(achievementId);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getAchievementByAdminUserId(int userId, int page, int limit){
        // 获取管辖内的所有userId
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserId(userId);

        // 根据userId 获取每个人的AchievementId
        List<Integer> achievementIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            List<AchievementPO> achievements = achievementDAO.getTotalAchievementByUserId(userIdList.get(i));
            for(int j = 0;j < achievements.size(); ++j){
                achievementIdList.add(achievements.get(j).getAchievement_id());
            }
        }
        // 对achievementId 去重
        achievementIdList = achievementIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, achievementIdList.size()); ++i){
            AchievementPO achievement = achievementDAO.getAchievementById(achievementIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(achievement.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("achievement_id", achievement.getAchievement_id());
            temp.put("title", achievement.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", achievementIdList.size());
        result.put("achievements", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAchievementByAdminUserIdAndName(int userId, String name, int page, int limit){
        // 获取管辖内的所有userId
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserId(userId);

        // 根据userId 和 name 获取每个人的AchievementId
        List<Integer> achievementIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
            if(user.getName().equals(name)){
                List<AchievementPO> achievements = achievementDAO.getTotalAchievementByUserId(userIdList.get(i));
                for(int j = 0;j < achievements.size(); ++j){
                    achievementIdList.add(achievements.get(j).getAchievement_id());
                }
            }
        }
        // 对achievementId 去重
        achievementIdList = achievementIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, achievementIdList.size()); ++i){
            AchievementPO achievement = achievementDAO.getAchievementById(achievementIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(achievement.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("achievement_id", achievement.getAchievement_id());
            temp.put("title", achievement.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", achievementIdList.size());
        result.put("achievements", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAchievementByAdminUserIdAndOrganizationId(int userId, int organizationId, int isOrganization,int page, int limit){
        // 判断权限
        Boolean permission = userDAO.judgePermissionOnAnOrganization(userId, organizationId, isOrganization);
        if(permission == false){
            return new ResultInfo(0, "没有该机构权限", new HashMap<>());
        }

        // 获取该Organization管辖到的用户
        List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(userId, organizationId, isOrganization);

        // 获取 achievementIdList
        List<Integer> achievementIdList = new ArrayList<>();
        for(int i = 0;i < userIdList.size(); ++i){
            List<AchievementPO> achievements = achievementDAO.getTotalAchievementByUserId(userIdList.get(i));
            for(int j = 0;j < achievements.size(); ++j){
                achievementIdList.add(achievements.get(j).getAchievement_id());
            }
        }
        // 对achievementId 去重
        achievementIdList = achievementIdList.stream().distinct().collect(Collectors.toList());

        // 组织[(page - 1) * limit, page * limit)的答案
        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, achievementIdList.size()); ++i){
            AchievementPO achievement = achievementDAO.getAchievementById(achievementIdList.get(i)).get(0);
            UserPO thisUser = userDAO.getUserById(achievement.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("achievement_id", achievement.getAchievement_id());
            temp.put("title", achievement.getTitle());
            temp.put("name", thisUser.getName());
            tempList.add(temp);
        }
        result.put("count", achievementIdList.size());
        result.put("achievements", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAchievementByUserIdAndTimeLimit(int userId, String timeLimit, int page, int limit){
        String[] time = timeLimit.split("~");
        String beginTimeStr = time[0].strip() + "-01";
        String[] endTimeList = time[1].strip().split("-");
        String endTimeStr = endTimeList[0] + "-" + endTimeList[1] + "-" + TimeTools.getLastDayOfMonth(parseInt(endTimeList[0]), parseInt(endTimeList[1]));

        List<AchievementPO> achievements = achievementDAO.getAchievementByUserIdAndTimeLimit(userId, beginTimeStr, endTimeStr);

        Map<String, Object>result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, achievements.size()); ++i){
            AchievementPO achievement = achievements.get(i);
            UserPO thisUser = userDAO.getUserById(achievement.getCreator()).get(0);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("achievement_id", achievement.getAchievement_id());
            temp.put("title", achievement.getTitle());
            temp.put("name", thisUser.getName());
            temp.put("submit_time", formatter.format(achievement.getCreateTime()));
            tempList.add(temp);
        }
        result.put("count", achievements.size());
        result.put("achievements", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo exportAchievementExcelFile(String name, int organizationId, int isOrganization, String timeLimit, HttpServletResponse response){
        String[] time = timeLimit.split("~");
        String beginTimeStr = time[0].strip() + "-01";
        String[] endTimeList = time[1].strip().split("-");
        String endTimeStr = endTimeList[0] + "-" + endTimeList[1] + "-" + TimeTools.getLastDayOfMonth(parseInt(endTimeList[0]), parseInt(endTimeList[1]));
        List<Integer> achievementIdList = new ArrayList<>();
        if(name.equals("")){
            List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(1, organizationId, isOrganization);
            // 获取planIdList
            for(int i = 0;i < userIdList.size(); ++i){
                List<AchievementPO> achievements = achievementDAO.getAchievementByUserIdAndTimeLimit(userIdList.get(i), beginTimeStr, endTimeStr);
                for(int j = 0;j < achievements.size(); ++j) {
                    achievementIdList.add(achievements.get(j).getAchievement_id());
                }
            }
            // 对planId 去重
            achievementIdList = achievementIdList.stream().distinct().collect(Collectors.toList());
        } else {
            List<Integer> userIdList = userService.getTotalManageUserIdByAdminUserIdAndOrganizationId(1, organizationId, isOrganization);
            // 获取planIdList
            for(int i = 0;i < userIdList.size(); ++i){
                UserPO user = userDAO.getUserById(userIdList.get(i)).get(0);
                if(!user.getName().equals(name))continue;
                List<AchievementPO> achievements = achievementDAO.getAchievementByUserIdAndTimeLimit(userIdList.get(i), beginTimeStr, endTimeStr);
                for(int j = 0;j < achievements.size(); ++j) {
                    achievementIdList.add(achievements.get(j).getAchievement_id());
                }
            }
            // 对planId 去重
            achievementIdList = achievementIdList.stream().distinct().collect(Collectors.toList());
        }
        List<List<String>> excelInfo = new ArrayList<>();
        List<String> title = new ArrayList<>();
        title.add("晒绩标题");title.add("姓名");title.add("年份");title.add("季度");title.add("创建时间");title.add("最后更新时间");title.add("晒绩内容1");title.add("晒绩内容2");title.add("晒绩内容3");
        title.add("电话号码");title.add("所属单位");title.add("考核单位");title.add("考核组");
        for(int i = 0;i < achievementIdList.size(); ++i){
            AchievementPO achievement = achievementDAO.getAchievementById(achievementIdList.get(i)).get(0);
            List<String> temp = new ArrayList<>();
            temp.add(achievement.getTitle());
            UserPO user = userDAO.getUserById(achievement.getCreator()).get(0);
            temp.add(user.getName());
            temp.add(String.valueOf(achievement.getYear()));
            temp.add(String.valueOf(achievement.getQuarter()));
            temp.add(formatter.format(achievement.getCreateTime()));
            temp.add(formatter.format(achievement.getUpdateTime()));
            temp.add(achievement.getText_1());
            temp.add(achievement.getText_2());
            temp.add(achievement.getText_3());
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

        String fileName = "晒绩表";

        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            fileName = organization.getName() + timeLimit + "晒绩";
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            fileName = assessGroup.getName() + timeLimit + "晒绩";
        }

        XSSFWorkbook wb = ExcelUtil.getXSSFWorkbook("晒绩表", title, excelInfo);

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
