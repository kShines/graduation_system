package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.AssessGroupService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class AssessGroupController {

    @Autowired
    private AssessGroupService assessGroupService;

    @RequestMapping(value="createAssessGroup", method = RequestMethod.POST)
    public ResultInfo createAssessGroup(@RequestBody Map<String, Object>jsonParam){
        return assessGroupService.createAssessGroup(jsonParam);
    }

    @RequestMapping(value="updateAssessGroup", method = RequestMethod.POST)
    public ResultInfo updateAssessGroup(@RequestBody Map<String, Object>jsonParam){
        return assessGroupService.updateAssessGroup(jsonParam);
    }

    @RequestMapping(value="deleteAssessGroupById", method = RequestMethod.POST)
    public ResultInfo deleteAssessGroupById(@RequestBody Map<String, Object> jsonParam){
        return assessGroupService.deleteAssessGroupById((int)jsonParam.get("group_id"));
    }

    @RequestMapping(value="getAssessGroupByOrganizationId", method = RequestMethod.POST)
    public ResultInfo getAssessGroupByOrganizationId(@RequestBody Map<String, Object> jsonParam){
        return assessGroupService.getAssessGroupByOrganizationId(
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="newAssessGroup", method = RequestMethod.POST)
    public ResultInfo newAssessGroup(@RequestBody Map<String, Object>jsonParam){
        return assessGroupService.newAssessGroup(
                (String)jsonParam.get("name"),
                (int)jsonParam.get("organization_id")
        );
    }

    @RequestMapping(value="getTotalAssessGroup", method = RequestMethod.POST)
    public ResultInfo getTotalAssessGroup(){
        return assessGroupService.getTotalAssessGroup();
    }
}
