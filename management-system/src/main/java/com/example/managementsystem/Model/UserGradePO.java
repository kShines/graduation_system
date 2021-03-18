package com.example.managementsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Data
@Table(name="user_grade")
@NoArgsConstructor
@AllArgsConstructor
public class UserGradePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int grade_id;

    @Column(name="user_id")
    Integer userId;

    @Column(name="grade_rank")
    String gradeRank;

    @Column(name="leader_grade")
    String leaderGrade;

    @Column(name="democracy_grade")
    String democracyGrade;

    @Column(name="assess_batch_id")
    Integer assessBatchId;
}
