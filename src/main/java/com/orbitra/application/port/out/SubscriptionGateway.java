package com.orbitra.application.port.out;

import com.orbitra.domain.entity.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionGateway {
    Subscription save(Subscription subscription);

    Optional<Subscription> findById(UUID id);

    List<Subscription> findAll();
}
