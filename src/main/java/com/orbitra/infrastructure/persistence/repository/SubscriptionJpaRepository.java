package com.orbitra.infrastructure.persistence.repository;

import com.orbitra.infrastructure.persistence.entity.SubscriptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionJpaEntity, UUID> {
}
