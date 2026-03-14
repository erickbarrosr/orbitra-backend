package com.orbitra.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpgradeSubscriptionRequest(@NotNull UUID planId) {
}
