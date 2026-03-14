package com.orbitra.infrastructure.persistence.adapter;

import com.orbitra.application.port.out.PlanGateway;
import com.orbitra.domain.entity.Plan;
import com.orbitra.infrastructure.persistence.entity.PlanJpaEntity;
import com.orbitra.infrastructure.persistence.repository.PlanJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PlanPersistenceAdapter implements PlanGateway {

    private final PlanJpaRepository planJpaRepository;

    public PlanPersistenceAdapter(PlanJpaRepository planJpaRepository) {
        this.planJpaRepository = planJpaRepository;
    }

    @Override
    public Plan save(Plan plan) {
        PlanJpaEntity entity = toJpa(plan);
        return toDomain(planJpaRepository.save(entity));
    }

    @Override
    public Optional<Plan> findById(UUID id) {
        return planJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Plan> findAll() {
        return planJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByName(String name) {
        return planJpaRepository.existsByNameIgnoreCase(name);
    }

    private PlanJpaEntity toJpa(Plan plan) {
        PlanJpaEntity entity = new PlanJpaEntity();
        entity.setId(plan.getId());
        entity.setName(plan.getName());
        entity.setPrice(plan.getPrice());
        entity.setCreatedAt(plan.getCreatedAt());
        return entity;
    }

    private Plan toDomain(PlanJpaEntity entity) {
        return new Plan(entity.getId(), entity.getName(), entity.getPrice(), entity.getCreatedAt());
    }
}
