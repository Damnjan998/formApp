package com.formapp.damnjan.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "statistic")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "number_of_populated_forms")
    private Integer numberOfPopulatedForms;
}
