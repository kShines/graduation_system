package com.example.managementsystem.DAO;

import com.example.managementsystem.Model.OrganizationPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrganizationDAO {
    List<OrganizationPO> getOrganizationById(int organizationId);

    List<OrganizationPO> getTotalOrganization();

    int updateOrganization(OrganizationPO organization);

    List<Integer> getHighestOrganizationNode(int nowOrganizationId, String[] adminOrganizationStr);

    List<Integer> getHighestAssessGroupNode(int nowOrganizationId, String[] adminOrganizationStr, String[] adminAssessGroupStr);

    Map<String, Object> constructTreeNode(int nowOrganizationId);

    void deleteOrganization(int organizationId);
}
