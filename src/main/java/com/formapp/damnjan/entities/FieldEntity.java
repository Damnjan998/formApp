package com.formapp.damnjan.entities;

import com.formapp.damnjan.enumes.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "field")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class FieldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "order_display")
    private Integer orderDisplay;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private FormEntity formEntity;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    @CreatedBy
    private UserEntity createdByUser;

    @Column(name = "last_updated_by_user_id")
    private Integer lastUserToModify;

}
