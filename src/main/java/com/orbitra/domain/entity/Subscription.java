package com.orbitra.domain.entity;

import com.orbitra.domain.enumtype.SubscriptionStatus;
import com.orbitra.domain.exception.DomainException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Subscription {

    private final UUID id;
    private final LocalDate startDate;
    private final LocalDateTime createdAt;
    private Plan plan;
    private SubscriptionStatus status;
    private LocalDate nextBillingDate;

    public Subscription(UUID id, Plan plan, LocalDate startDate, LocalDateTime createdAt) {
        this(id, plan, SubscriptionStatus.ACTIVE, startDate, startDate.plusMonths(1), createdAt);
    }

    public static Subscription restore(
            UUID id,
            Plan plan,
            SubscriptionStatus status,
            LocalDate startDate,
            LocalDate nextBillingDate,
            LocalDateTime createdAt
    ) {
        return new Subscription(id, plan, status, startDate, nextBillingDate, createdAt);
    }

    private Subscription(
            UUID id,
            Plan plan,
            SubscriptionStatus status,
            LocalDate startDate,
            LocalDate nextBillingDate,
            LocalDateTime createdAt
    ) {

        if (id == null) {
            throw new DomainException("Subscription ID cannot be null");
        }

        if (plan == null) {
            throw new DomainException("Plan cannot be null");
        }

        if (status == null) {
            throw new DomainException("Status cannot be null");
        }

        if (startDate == null) {
            throw new DomainException("Start date cannot be null");
        }

        if (nextBillingDate == null) {
            throw new DomainException("Next billing date cannot be null");
        }

        if (createdAt == null) {
            throw new DomainException("Creation date cannot be null");
        }

        this.id = id;
        this.plan = plan;
        this.status = status;
        this.startDate = startDate;
        this.nextBillingDate = nextBillingDate;
        this.createdAt = createdAt;
    }

    public void cancel() {
        if (this.status != SubscriptionStatus.ACTIVE) {
            throw new DomainException("Only active subscriptions can be canceled");
        }
        this.status = SubscriptionStatus.CANCELED;
    }

    public void upgrade(Plan newPlan) {
        if (this.status != SubscriptionStatus.ACTIVE) {
            throw new DomainException("Only active subscriptions can be upgraded");
        }

        if (newPlan == null) {
            throw new DomainException("New plan cannot be null");
        }

        this.plan = newPlan;
        this.status = SubscriptionStatus.UPGRADED;
    }

    public void markAsPastDue() {
        if (this.status != SubscriptionStatus.ACTIVE) {
            throw new DomainException("Only active subscriptions can become past due");
        }
        this.status = SubscriptionStatus.PAST_DUE;
    }

    public void registerPayment() {
        if (this.status == SubscriptionStatus.CANCELED) {
            throw new DomainException("Canceled subscriptions cannot receive payments");
        }

        this.nextBillingDate = this.nextBillingDate.plusMonths(1);
        this.status = SubscriptionStatus.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getNextBillingDate() {
        return nextBillingDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription that = (Subscription) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
