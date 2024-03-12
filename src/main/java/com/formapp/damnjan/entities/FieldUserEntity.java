package com.formapp.damnjan.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Entity
@Table(name = "field_user")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text_value")
    private String textValue;

    @Column(name = "number_value")
    @Nullable
    private Double numberValue;

    @Column(name = "created_at")
    @Nullable
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @CreatedBy
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
