package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.OrganizationService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value="getAllOrganization", method = RequestMethod.POST)
    public ResultInfo getAllOrganization(){
        return organizationService.getTotalOrganization();
    }

    @RequestMapping(value="updateOrganizationManageArea", method = RequestMethod.POST)
    public ResultInfo updateOrganizationManageArea(@RequestBody Map<String, Object> jsonParam){
        return organizationService.updateOrganizationManageArea(
                (int)jsonParam.get("organization_id"),
                (String)jsonParam.get("manage_organization_area"),
                (String)jsonParam.get("manage_assess_group_area")
        );
    }

    @RequestMapping(value="createOrganization", method = RequestMethod.POST)
    public ResultInfo createOrganization(@RequestBody Map<String, Object> jsonParam){
        return organizationService.createOrganization(jsonParam);
    }

    @RequestMapping(value="getOrganizationTreeByUserId", method = RequestMethod.POST)
    public ResultInfo getOrganizationTreeByUserId(@RequestBody Map<String, Object> jsonParam){
        return organizationService.getOrganizationTreeByUserId(
                (int)jsonParam.get("user_id")
        );
    }

    @RequestMapping(value="setOrganizationAdminUser", method = RequestMethod.POST)
    public ResultInfo setOrganizationAdminUser(@RequestBody Map<String, Object> jsonParam){
        return organizationService.setOrganizationAdminUser(
                (List<Map<String, Object>>) jsonParam.get("user_info"),
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization")
        );
    }

    @RequestMapping(value="deleteOrganizationAdminUser", method = RequestMethod.POST)
    public ResultInfo deleteOrganizationAdminUser(@RequestBody Map<String, Object> jsonParam){
        return organizationService.deleteOrganizationAdminUser(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization")
        );
    }

    @RequestMapping(value="deleteOrganizationById", method = RequestMethod.POST)
    public ResultInfo deleteOrganizationById(@RequestBody Map<String, Object> jsonParam){
        return organizationService.deleteOrganizationById(
                (int)jsonParam.get("organization_id")
        );
    }

    @RequestMapping(value="modifyOrganizationNameById", method = RequestMethod.POST)
    public ResultInfo modifyOrganizationNameById(@RequestBody Map<String, Object> jsonParam){
        return organizationService.modifyOrganizationNameById(
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (String)jsonParam.get("name")
        );
    }
}
