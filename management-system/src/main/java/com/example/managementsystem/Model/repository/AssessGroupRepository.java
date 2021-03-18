package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.AssessGroupPO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessGroupRepository extends CrudRepository<AssessGroupPO, Integer> {

    @Query("SELECT t FROM AssessGroupPO t WHERE t.group_id = :groupId")
    List<AssessGroupPO> getGroupById(@Param("groupId") int groupId);

    @Query("SELECT t FROM AssessGroupPO t")
    List<AssessGroupPO> getTotalAssessGroup();
}
