package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.AchievementService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value="api/")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @RequestMapping(value="getAllAchievements", method = RequestMethod.POST)
    public ResultInfo getAllAchievements(@RequestBody Map<String, Object> jsonParam){
        return achievementService.getAllAchievementsByUserId(
                (int)jsonParam.get("user_id")
                ,(int)jsonParam.get("page") - 1
                ,(int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value = "getAchievementByQuarter", method = RequestMethod.POST)
    public ResultInfo getAchievementByQuarter(@RequestBody Map<String, Object> jsonParam){
        return achievementService.getAchievementsByUserIdAndQuarter(
                (int)jsonParam.get("user_id")
                ,(int)jsonParam.get("year")
                ,(int)jsonParam.get("quarter")
                ,(int)jsonParam.get("page") - 1
                ,(int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value = "getAchievementById", method = RequestMethod.POST)
    public ResultInfo getAchievementById(@RequestBody Map<String, Object> jsonParam){
        return achievementService.getAchievementById((int)jsonParam.get("achievement_id"));
    }

    @RequestMapping(value = "createAchievement", method = RequestMethod.POST)
    public ResultInfo createAchievement(@RequestBody Map<String, Object> jsonParam){
        return achievementService.newAchievement(jsonParam);
    }

    @RequestMapping(value="updateAchievement", method = RequestMethod.POST)
    public ResultInfo updateAchievement(@RequestBody Map<String, Object> jsonParam){
        return achievementService.updateAchievement(jsonParam);
    }

    @RequestMapping(value="getAchievementByGroupId", method = RequestMethod.POST)
    public ResultInfo getAchievementByGroupId(@RequestBody Map<String, Object> jsonParam){
        return achievementService.getAchievementByGroupId(jsonParam);
    }

    @RequestMapping(value="getAchievementByName", method = RequestMethod.POST)
    public ResultInfo getAchievementByUserName(@RequestBody Map<String, Object> jsonParam){
        return achievementService.getAchievementByGroupIdAndUserName(jsonParam);
    }

    @RequestMapping(value="deleteAchievementById", method = RequestMethod.POST)
    public ResultInfo deleteAchievementById(@RequestBody Map<String, Object> jsonParam){
        return achievementService.deleteAchievementById((int)jsonParam.get("achievement_id"));
    }

    @RequestMapping(value="getAchievementByUserIdAndTimeLimit", method = RequestMethod.POST)
    public ResultInfo getAchievementByUserIdAndTimeLimit(@RequestBody Map<String, Object> jsonParam){
        return achievementService.getAchievementByUserIdAndTimeLimit(
                (int)jsonParam.get("user_id"),
                (String)jsonParam.get("time_limit"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }
    @RequestMapping(value="exportAchievementExcelFile", method = RequestMethod.GET, produces="application/octet-stream")
    public void exportAchievementExcelFile(String name, int organization_id, int is_organization, String time_limit, HttpServletResponse response){
        achievementService.exportAchievementExcelFile(
                name,
                organization_id,
                is_organization,
                time_limit,
                response
        );
    }
}
