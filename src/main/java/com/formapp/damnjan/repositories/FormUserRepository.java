package com.formapp.damnjan.repositories;

import com.formapp.damnjan.entities.FormUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface FormUserRepository extends JpaRepository<FormUserEntity, Integer> {

    int countByCreatedAtAfterAndCreatedAtBefore(Timestamp createdAt, Timestamp endOfDayTimestamp);
}
