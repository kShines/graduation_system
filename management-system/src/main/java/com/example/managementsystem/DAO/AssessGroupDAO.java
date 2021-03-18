package com.example.managementsystem.DAO;

import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.OrganizationPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessGroupDAO {
    List<AssessGroupPO> getGroupById(int groupId);

    int updateAssessGroup(AssessGroupPO assessGroup);

    void deleteAssessGroupById(int groupId);

    List<Integer> getTotalManageGroupIdByOrganizationId(int organizationId);

    List<AssessGroupPO> getTotalAssessGroup();
}
