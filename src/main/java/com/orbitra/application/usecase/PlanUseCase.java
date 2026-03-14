package com.orbitra.application.usecase;

import com.orbitra.application.dto.PlanOutput;
import com.orbitra.application.exception.BusinessException;
import com.orbitra.application.exception.NotFoundException;
import com.orbitra.application.port.out.PlanGateway;
import com.orbitra.domain.entity.Plan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PlanUseCase {

    private final PlanGateway planGateway;

    public PlanUseCase(PlanGateway planGateway) {
        this.planGateway = planGateway;
    }

    @Transactional
    public PlanOutput create(String name, BigDecimal price) {
        if (planGateway.existsByName(name)) {
            throw new BusinessException("Plan with this name already exists");
        }

        Plan plan = new Plan(UUID.randomUUID(), name, price, LocalDateTime.now());
        return toOutput(planGateway.save(plan));
    }

    @Transactional(readOnly = true)
    public Plan findById(UUID id) {
        return planGateway.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan not found"));
    }

    @Transactional(readOnly = true)
    public List<PlanOutput> listAll() {
        return planGateway.findAll().stream().map(this::toOutput).toList();
    }

    private PlanOutput toOutput(Plan plan) {
        return new PlanOutput(plan.getId(), plan.getName(), plan.getPrice(), plan.getCreatedAt());
    }
}
