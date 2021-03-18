package com.example.managementsystem.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="organization")
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int organization_id;

    @Column(name="admin_id")
    String adminIdList;

    @Column(name="name")
    String name;

    @Column(name="superior")
    Integer superior;

    @Column(name="sub_organization")
    String subOrganizationIdList;

    @Column(name="sub_group")
    String subGroupIdList;

    @Column(name="manage_organization_area")
    String manageOrganizationIdList;

    @Column(name="manage_assess_group_area")
    String manageAssessGroupIdList;
}
