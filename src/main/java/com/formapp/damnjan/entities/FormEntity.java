package com.formapp.damnjan.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "form")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class FormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "formEntity")
    private List<FormUserEntity> formUserEntityList;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    @CreatedBy
    private UserEntity createdByUser;

    @Column(name = "last_updated_by_user_id")
    private Integer lastUserToModify;
}
