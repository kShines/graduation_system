package com.example.managementsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="assess_group")
@NoArgsConstructor
@AllArgsConstructor
public class AssessGroupPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int group_id;

    @Column(name="name")
    String name;

    @Column(name="admin")
    String adminList;

    @Column(name="member")
    String memberList;

    @Column(name="create_time")
    Date createTime;

    @Column(name="superior")
    Integer superior;
}
