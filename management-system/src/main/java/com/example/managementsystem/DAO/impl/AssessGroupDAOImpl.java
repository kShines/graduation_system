package com.example.managementsystem.DAO.impl;

import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.OrganizationPO;
import com.example.managementsystem.Model.repository.AssessGroupRepository;
import com.example.managementsystem.Model.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Component
public class AssessGroupDAOImpl implements AssessGroupDAO {

    @Autowired
    private AssessGroupRepository assessGroupRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<AssessGroupPO> getGroupById(int groupId) {
        return assessGroupRepository.getGroupById(groupId);
    }

    public int updateAssessGroup(AssessGroupPO assessGroup) {
        if(assessGroup.getAdminList() == null) assessGroup.setAdminList("");
        if(assessGroup.getMemberList() == null) assessGroup.setMemberList("");
        assessGroupRepository.save(assessGroup);
        return assessGroup.getGroup_id();
    }

    public void deleteAssessGroupById(int groupId) {
        assessGroupRepository.deleteById(groupId);
    }

    @Override
    public List<Integer> getTotalManageGroupIdByOrganizationId(int organizationId) {
        OrganizationPO organization = organizationRepository.getOrganizationById(organizationId).get(0);
        List<Integer> assessGroupIdList = new ArrayList<>();
        String[] adminAssessGroupStrList = organization.getSubGroupIdList().split(",");
        String[] adminOrganizationStrList = organization.getSubOrganizationIdList().split(",");
        for(int i = 0;i < adminAssessGroupStrList.length;++i){
            if(adminAssessGroupStrList[i].equals(""))continue;
            assessGroupIdList.add(parseInt(adminAssessGroupStrList[i]));
        }
        // 递归调用
        for(int i = 0;i < adminOrganizationStrList.length; ++i){
            if(adminOrganizationStrList[i].equals(""))continue;
            assessGroupIdList.addAll(getTotalManageGroupIdByOrganizationId(parseInt(adminOrganizationStrList[i])));
        }
        // 对assessGroupIdList 去重 并返回
        return assessGroupIdList.stream().distinct().collect(Collectors.toList());
    }

    public List<AssessGroupPO> getTotalAssessGroup() {
        return assessGroupRepository.getTotalAssessGroup();
    }

}
