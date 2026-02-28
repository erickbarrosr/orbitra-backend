package com.orbitra.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateSubscriptionRequest(
        @NotNull UUID planId,
        @NotNull LocalDate startDate
) {
}
