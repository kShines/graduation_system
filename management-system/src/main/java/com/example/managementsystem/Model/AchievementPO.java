package com.example.managementsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="achievement")
@NoArgsConstructor
@AllArgsConstructor
public class AchievementPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int achievement_id;

    @Column(name="creator")
    Integer creator;

    @Column(name="year")
    Integer year;

    @Column(name="quarter")
    Integer quarter;

    @Column(name="month")
    Integer month;

    @Column(name="update_time")
    Date updateTime;

    @Column(name="create_time")
    Date createTime;

    @Column(name="title")
    String title;

    @Column(name="text_1")
    String text_1;

    @Column(name="text_2")
    String text_2;

    @Column(name="text_3")
    String text_3;
}
