package com.formapp.damnjan.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "field_user")
@Getter
@Setter
public class FieldUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text_value")
    private String textValue;

    @Column(name = "number_value")
    private Double numberValue;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "field_id")
    private FieldEntity fieldEntity;

    // private Integer formUserId; todo: See if it's many to many

}
