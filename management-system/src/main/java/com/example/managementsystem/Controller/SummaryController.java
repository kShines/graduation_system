package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.SummaryService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @RequestMapping(value="getAllSummary", method = RequestMethod.POST)
    public ResultInfo getAllSummary(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByUserId(
                (int)jsonParam.get("user_id")
                ,(int)jsonParam.get("page") - 1
                ,(int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value = "getSummaryByQuarter", method = RequestMethod.POST)
    public ResultInfo getPlansByQuarter(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByUserIdAndQuarter(
                (int)jsonParam.get("user_id")
                ,(int)jsonParam.get("year")
                ,(int)jsonParam.get("quarter")
                ,(int)jsonParam.get("page") - 1
                ,(int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value = "getSummaryById", method = RequestMethod.POST)
    public ResultInfo getSummaryById(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryById((int)jsonParam.get("summary_id"));
    }

    @RequestMapping(value = "createSummary", method = RequestMethod.POST)
    public ResultInfo createSummary(@RequestBody Map<String, Object> jsonParam){
        return summaryService.newSummary(jsonParam);
    }

    @RequestMapping(value="updateSummary", method = RequestMethod.POST)
    public ResultInfo updateSummary(@RequestBody Map<String, Object> jsonParam){
        return summaryService.updateSummary(jsonParam);
    }

    @RequestMapping(value="getSummaryByGroupId", method = RequestMethod.POST)
    public ResultInfo getSummaryByGroupId(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByGroupId(jsonParam);
    }

    @RequestMapping(value="getSummaryByName", method = RequestMethod.POST)
    public ResultInfo getSummaryByName(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByGroupIdAndUserName(jsonParam);
    }

    @RequestMapping(value="deleteSummaryById", method = RequestMethod.POST)
    public ResultInfo deleteSummaryById(@RequestBody Map<String, Object> jsonParam){
        return summaryService.deleteSummaryById(jsonParam);
    }

    @RequestMapping(value="getSummaryByAdminUserId", method = RequestMethod.POST)
    public ResultInfo getSummaryByAdminUserId(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByAdminUserId(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getSummaryByAdminUserIdAndName", method = RequestMethod.POST)
    public ResultInfo getSummaryByAdminUserIdAndName(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByAdminUserIdAndName(
                (int)jsonParam.get("user_id"),
                (String)jsonParam.get("name"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getSummaryByAdminUserIdAndOrganizationId", method = RequestMethod.POST)
    public ResultInfo getSummaryByAdminUserIdAndOrganizationId(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByAdminUserIdAndOrganizationId(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getSummaryByUserIdAndTimeLimit", method = RequestMethod.POST)
    public ResultInfo getSummaryByUserIdAndTimeLimit(@RequestBody Map<String, Object> jsonParam){
        return summaryService.getSummaryByUserIdAndTimeLimit(
                (int)jsonParam.get("user_id"),
                (String)jsonParam.get("time_limit"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="exportSummaryExcelFile", method = RequestMethod.GET, produces="application/octet-stream")
    public void exportSummaryExcelFile(String name, int organization_id, int is_organization, String time_limit, HttpServletResponse response){
        summaryService.exportSummaryExcelFile(
                name,
                organization_id,
                is_organization,
                time_limit,
                response
        );
    }
}
