package com.formapp.damnjan.repositories;

import com.formapp.damnjan.entities.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Integer> {
}
