package com.example.managementsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
public class UserPO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int user_id;

    @Column(name="username")
    String username;

    @Column(name="password")
    String password;

    @Column(name="id_number")
    String idNumber;

    @Column(name="name")
    String name;

    @Column(name="organization")
    Integer organization;

    @Column(name="permission")
    String permission;

    @Column(name="position")
    String position;

    @Column(name="sex")
    Integer sex;

    @Column(name="assess_organization")
    Integer assessOrganizationId;

    @Column(name="assess_group")
    Integer assessGroupId;

    @Column(name="group_start_time")
    Date groupStartTime;

    @Column(name="is_assess")
    Integer isAssess;

    @Column(name="admin_organization")
    String adminOrganization;

    @Column(name="admin_assess_group")
    String adminAssessGroup;

    @Column(name="assess_position")
    String assessPosition;
}
