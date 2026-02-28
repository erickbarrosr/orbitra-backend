package com.orbitra.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PlanOutput(UUID id, String name, BigDecimal price, LocalDateTime createdAt) {
}
