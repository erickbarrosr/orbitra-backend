package com.orbitra.application.dto;

import com.orbitra.domain.enumtype.SubscriptionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record SubscriptionOutput(
        UUID id,
        UUID planId,
        String planName,
        SubscriptionStatus status,
        LocalDate startDate,
        LocalDate nextBillingDate,
        LocalDateTime createdAt
) {
}
