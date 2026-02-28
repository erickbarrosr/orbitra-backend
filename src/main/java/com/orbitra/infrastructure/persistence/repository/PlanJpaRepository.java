package com.orbitra.infrastructure.persistence.repository;

import com.orbitra.infrastructure.persistence.entity.PlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanJpaRepository extends JpaRepository<PlanJpaEntity, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
