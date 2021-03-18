package com.example.managementsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="plan")
@NoArgsConstructor
@AllArgsConstructor
public class PlanPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int plan_id;

    @Column(name="creator")
    Integer creator;

    @Column(name="year")
    Integer year;

    @Column(name="quarter")
    Integer quarter;

    @Column(name="update_time")
    Date updateTime;

    @Column(name="title")
    String title;

    @Column(name="text")
    String text;

    @Column(name="create_time")
    Date createTime;

}
