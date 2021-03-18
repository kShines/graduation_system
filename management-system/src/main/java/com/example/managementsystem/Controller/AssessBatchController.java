package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.AssessBatchService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class AssessBatchController {

    @Autowired
    private AssessBatchService assessBatchService;

    @RequestMapping(value="getAllAssessBatchByGroupId", method = RequestMethod.POST)
    public ResultInfo getAllAssessBatchByGroupId(@RequestBody Map<String, Object> jsonParam){
        return assessBatchService.getAllAssessBatch(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="createAssessBatch", method = RequestMethod.POST)
    public ResultInfo createAssessBatch(@RequestBody Map<String, Object> jsonParam){
        return assessBatchService.createAssessBatch(jsonParam);
    }

    @RequestMapping(value="updateAssessBatch", method = RequestMethod.POST)
    public ResultInfo updateAssessBatch(@RequestBody Map<String, Object> jsonParam){
        return assessBatchService.updateAssessBatch(jsonParam);
    }

    @RequestMapping(value="deleteAssessBatch", method = RequestMethod.POST)
    public ResultInfo deleteAssessBatch(@RequestBody Map<String, Object> jsonParam){
        return assessBatchService.deleteAssessBatchById((int)jsonParam.get("assess_batch_id"));
    }

    @RequestMapping(value="getAssessBatchByTitle", method = RequestMethod.POST)
    public ResultInfo getAssessBatchByUserIdAndTitle(@RequestBody Map<String, Object> jsonParam){
        return assessBatchService.getAssessBatchByUserIdAndTitle(
                (int)jsonParam.get("user_id"),
                (String)jsonParam.get("title"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getAdminAssessBatchByOrganizationId", method = RequestMethod.POST)
    public ResultInfo getAdminAssessBatchByOrganizationId(@RequestBody Map<String, Object> jsonParam){
        return assessBatchService.getAdminAssessBatchByOrganizationId(
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getAssessBatchByOrganizationIdAndTitle", method = RequestMethod.POST)
    public ResultInfo getAssessBatchByOrganizationIdAndTitle(@RequestBody Map<String, Object>jsonParam){
        return assessBatchService.getAssessBatchByOrganizationIdAndTitle(
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (String)jsonParam.get("title"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

}
