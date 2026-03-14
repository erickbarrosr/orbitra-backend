package com.orbitra.application.usecase;

import com.orbitra.application.dto.SubscriptionOutput;
import com.orbitra.application.exception.NotFoundException;
import com.orbitra.application.port.out.SubscriptionGateway;
import com.orbitra.domain.entity.Plan;
import com.orbitra.domain.entity.Subscription;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionUseCase {

    private final SubscriptionGateway subscriptionGateway;
    private final PlanUseCase planUseCase;

    public SubscriptionUseCase(SubscriptionGateway subscriptionGateway, PlanUseCase planUseCase) {
        this.subscriptionGateway = subscriptionGateway;
        this.planUseCase = planUseCase;
    }

    @Transactional
    public SubscriptionOutput create(UUID planId, LocalDate startDate) {
        Plan plan = planUseCase.findById(planId);
        Subscription subscription = new Subscription(UUID.randomUUID(), plan, startDate, LocalDateTime.now());
        return toOutput(subscriptionGateway.save(subscription));
    }

    @Transactional
    public SubscriptionOutput cancel(UUID subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        subscription.cancel();
        return toOutput(subscriptionGateway.save(subscription));
    }

    @Transactional
    public SubscriptionOutput markPastDue(UUID subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        subscription.markAsPastDue();
        return toOutput(subscriptionGateway.save(subscription));
    }

    @Transactional
    public SubscriptionOutput registerPayment(UUID subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        subscription.registerPayment();
        return toOutput(subscriptionGateway.save(subscription));
    }

    @Transactional
    public SubscriptionOutput upgrade(UUID subscriptionId, UUID newPlanId) {
        Subscription subscription = getSubscription(subscriptionId);
        Plan newPlan = planUseCase.findById(newPlanId);
        subscription.upgrade(newPlan);
        return toOutput(subscriptionGateway.save(subscription));
    }

    @Transactional(readOnly = true)
    public SubscriptionOutput findById(UUID subscriptionId) {
        return toOutput(getSubscription(subscriptionId));
    }

    @Transactional(readOnly = true)
    public List<SubscriptionOutput> listAll() {
        return subscriptionGateway.findAll().stream().map(this::toOutput).toList();
    }

    private Subscription getSubscription(UUID subscriptionId) {
        return subscriptionGateway.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
    }

    private SubscriptionOutput toOutput(Subscription subscription) {
        return new SubscriptionOutput(
                subscription.getId(),
                subscription.getPlan().getId(),
                subscription.getPlan().getName(),
                subscription.getStatus(),
                subscription.getStartDate(),
                subscription.getNextBillingDate(),
                subscription.getCreatedAt()
        );
    }
}
