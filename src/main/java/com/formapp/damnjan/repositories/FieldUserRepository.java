package com.formapp.damnjan.repositories;

import com.formapp.damnjan.entities.FieldUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FieldUserRepository extends JpaRepository<FieldUserEntity, Integer> {

    Optional<FieldUserEntity> findByFormUserEntityIdAndFieldEntityId(Integer formUserEntityId, Integer fieldEntityId);
}
