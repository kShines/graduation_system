package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.OrganizationPO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<OrganizationPO, Integer> {
    @Query(value = "SELECT t FROM OrganizationPO t WHERE t.organization_id = :organizationId")
    List<OrganizationPO> getOrganizationById(@Param("organizationId")int organizationId);

    @Query(value = "SELECT t FROM OrganizationPO t")
    List<OrganizationPO> getTotalOrganization();
}
