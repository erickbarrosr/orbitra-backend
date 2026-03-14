package com.orbitra.application.port.out;

import com.orbitra.domain.entity.Plan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanGateway {
    Plan save(Plan plan);

    Optional<Plan> findById(UUID id);

    List<Plan> findAll();

    boolean existsByName(String name);
}
