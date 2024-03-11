package com.formapp.damnjan.repositories;

import com.formapp.damnjan.entities.FormEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Integer> {
    int countByCreatedAt(Timestamp createdAt);
    Page<FormEntity> findAll(Pageable pageable);
}
