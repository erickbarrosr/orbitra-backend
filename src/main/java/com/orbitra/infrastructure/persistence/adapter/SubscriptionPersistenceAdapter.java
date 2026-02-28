package com.orbitra.infrastructure.persistence.adapter;

import com.orbitra.application.port.out.SubscriptionGateway;
import com.orbitra.domain.entity.Plan;
import com.orbitra.domain.entity.Subscription;
import com.orbitra.infrastructure.persistence.entity.PlanJpaEntity;
import com.orbitra.infrastructure.persistence.entity.SubscriptionJpaEntity;
import com.orbitra.infrastructure.persistence.repository.PlanJpaRepository;
import com.orbitra.infrastructure.persistence.repository.SubscriptionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SubscriptionPersistenceAdapter implements SubscriptionGateway {

    private final SubscriptionJpaRepository subscriptionJpaRepository;
    private final PlanJpaRepository planJpaRepository;

    public SubscriptionPersistenceAdapter(
            SubscriptionJpaRepository subscriptionJpaRepository,
            PlanJpaRepository planJpaRepository
    ) {
        this.subscriptionJpaRepository = subscriptionJpaRepository;
        this.planJpaRepository = planJpaRepository;
    }

    @Override
    public Subscription save(Subscription subscription) {
        SubscriptionJpaEntity entity = toJpa(subscription);
        return toDomain(subscriptionJpaRepository.save(entity));
    }

    @Override
    public Optional<Subscription> findById(UUID id) {
        return subscriptionJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private SubscriptionJpaEntity toJpa(Subscription subscription) {
        PlanJpaEntity planEntity = planJpaRepository.getReferenceById(subscription.getPlan().getId());

        SubscriptionJpaEntity entity = new SubscriptionJpaEntity();
        entity.setId(subscription.getId());
        entity.setPlan(planEntity);
        entity.setStatus(subscription.getStatus());
        entity.setStartDate(subscription.getStartDate());
        entity.setNextBillingDate(subscription.getNextBillingDate());
        entity.setCreatedAt(subscription.getCreatedAt());
        return entity;
    }

    private Subscription toDomain(SubscriptionJpaEntity entity) {
        Plan plan = new Plan(
                entity.getPlan().getId(),
                entity.getPlan().getName(),
                entity.getPlan().getPrice(),
                entity.getPlan().getCreatedAt()
        );

        return Subscription.restore(
                entity.getId(),
                plan,
                entity.getStatus(),
                entity.getStartDate(),
                entity.getNextBillingDate(),
                entity.getCreatedAt()
        );
    }
}
