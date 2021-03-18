package com.example.managementsystem.Model.tempQueryEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryEntity {
    @Id
    @GeneratedValue
    int id;

    @Column
    String title;

    @Column
    String name;
}
