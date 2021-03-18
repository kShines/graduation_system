package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.OrganizationDAO;
import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.OrganizationPO;
import com.example.managementsystem.Model.repository.AssessGroupRepository;
import com.example.managementsystem.Model.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Component
public class OrganizationDAOImpl implements OrganizationDAO {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AssessGroupRepository assessGroupRepository;

    public List<OrganizationPO> getOrganizationById(int organizationId) {
        return organizationRepository.getOrganizationById(organizationId);
    }

    public List<OrganizationPO> getTotalOrganization() {
        return organizationRepository.getTotalOrganization();
    }

    public int updateOrganization(OrganizationPO organization) {
        if(organization.getSubGroupIdList() == null) organization.setSubGroupIdList("");
        if(organization.getSubOrganizationIdList() == null) organization.setSubOrganizationIdList("");
        if(organization.getAdminIdList() == null) organization.setAdminIdList("");
        if(organization.getManageAssessGroupIdList() == null) organization.setManageAssessGroupIdList("");
        if(organization.getManageOrganizationIdList() == null) organization.setManageOrganizationIdList("");
        // organizationRepository.save(organization);
        organization = organizationRepository.save(organization);
        return organization.getOrganization_id();
    }


    public List<Integer> getHighestOrganizationNode(int nowOrganizationId, String[] adminOrganizationStr) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0;i < adminOrganizationStr.length; ++i){
            if(adminOrganizationStr[i].equals(""))continue;
            if(parseInt(adminOrganizationStr[i]) == nowOrganizationId){
                result.add(nowOrganizationId);
                return result;
            }
        }
        OrganizationPO organization = organizationRepository.getOrganizationById(nowOrganizationId).get(0);
        String[] subOrganizationIdStr = organization.getSubOrganizationIdList().split(",");
        for(int i = 0;i < subOrganizationIdStr.length; ++i){
            if(subOrganizationIdStr[i].equals(""))continue;
            result.addAll(getHighestOrganizationNode(parseInt(subOrganizationIdStr[i]), adminOrganizationStr));
        }
        return result;
    }

    public List<Integer> getHighestAssessGroupNode(int nowOrganizationId, String[] adminOrganizationStr, String[] adminAssessGroupStr) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0;i < adminOrganizationStr.length; ++i){
            if(adminOrganizationStr[i].equals(""))continue;
            if(parseInt(adminOrganizationStr[i]) == nowOrganizationId){
                return result;
            }
        }
        OrganizationPO organization = organizationRepository.getOrganizationById(nowOrganizationId).get(0);
        String[] subOrganizationIdStr = organization.getSubOrganizationIdList().split(",");
        String[] subAssessGroupIdStr = organization.getSubGroupIdList().split(",");
        for(int i = 0;i < subOrganizationIdStr.length; ++i){
            if(subAssessGroupIdStr[i].equals(""))continue;
            result.addAll(getHighestAssessGroupNode(parseInt(subOrganizationIdStr[i]), adminOrganizationStr, adminAssessGroupStr));
        }
        for(int i = 0;i < subAssessGroupIdStr.length; ++i){
            if(subAssessGroupIdStr[i].equals(""))continue;
            for(int j = 0;j < adminAssessGroupStr.length; ++j){
                if(adminAssessGroupStr[j].equals(""))continue;
                if(subAssessGroupIdStr[i].equals(adminAssessGroupStr[j])){
                    result.add(parseInt(adminAssessGroupStr[j]));
                    break;
                }
            }
        }
        return result;
    }

    public Map<String, Object> constructTreeNode(int nowOrganizationId) {
        Map<String, Object> result = new HashMap<>();
        OrganizationPO organization = organizationRepository.getOrganizationById(nowOrganizationId).get(0);
        result.put("title", organization.getName());
        result.put("id", organization.getOrganization_id());
        result.put("node_type", "organization");
        List<Map<String, Object>> children = new ArrayList<>();
        String[] subOrganizationIdStr = organization.getSubOrganizationIdList().split(",");
        String[] subAssessGroupIdStr = organization.getSubGroupIdList().split(",");
        for(int i = 0;i < subOrganizationIdStr.length; ++i){
            if(subOrganizationIdStr[i].equals(""))continue;
            children.add(constructTreeNode(parseInt(subOrganizationIdStr[i])));
        }
        for(int i = 0;i < subAssessGroupIdStr.length; ++i){
            if(subAssessGroupIdStr[i].equals(""))continue;
            Map<String, Object> temp = new HashMap<>();
            AssessGroupPO assessGroup = assessGroupRepository.getGroupById(parseInt(subAssessGroupIdStr[i])).get(0);
            temp.put("title", assessGroup.getName());
            temp.put("id", assessGroup.getGroup_id());
            temp.put("node_type", "assess_group");
            children.add(temp);
        }
        result.put("children", children);
        return result;
    }

    public void deleteOrganization(int organizationId) {
        organizationRepository.deleteById(organizationId);
    }
}
