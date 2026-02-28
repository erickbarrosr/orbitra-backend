package com.orbitra.domain.entity;

import com.orbitra.domain.exception.DomainException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Plan {

    private final UUID id;
    private final LocalDateTime createdAt;
    private String name;
    private BigDecimal price;

    public Plan(UUID id, String name, BigDecimal price, LocalDateTime createdAt) {

        if (id == null) {
            throw new DomainException("Plan ID cannot be null");
        }

        if (name == null || name.isBlank()) {
            throw new DomainException("Plan name cannot be empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Plan price must be positive");
        }

        if (createdAt == null) {
            throw new DomainException("Creation date cannot be null");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("New price must be positive");
        }
        this.price = newPrice;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plan)) return false;
        Plan plan = (Plan) o;
        return id.equals(plan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}