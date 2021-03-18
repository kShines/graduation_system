package com.example.managementsystem.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="assess_batch")
public class AssessBatchPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int assess_batch_id;

    @Column(name="group_id")
    Integer groupId;

    @Column(name="year")
    Integer year;

    @Column(name="quarter")
    Integer quarter;

    @Column(name="title")
    String title;

    @Column(name="is_use")
    Integer is_use;
}
