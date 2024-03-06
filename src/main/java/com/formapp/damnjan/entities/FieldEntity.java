package com.formapp.damnjan.entities;

import com.formapp.damnjan.enumes.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "field")
@Getter
@Setter
public class FieldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "order_display")
    private Integer orderDisplay;

    @Column(name = "type")
    private Type type;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

//    private Integer formId; todo: See if it's many to many

}
