package com.formapp.damnjan.repositories;

import com.formapp.damnjan.entities.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Integer> {
}
