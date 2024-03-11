package com.formapp.damnjan.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

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

    @ManyToOne
    @JoinColumn(name = "form_user_id")
    private FormUserEntity formUserEntity;

    @Column(name = "last_user_to_modify")
    private Integer lastUserToModify;
}
