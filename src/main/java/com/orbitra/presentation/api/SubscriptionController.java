package com.orbitra.presentation.api;

import com.orbitra.application.dto.SubscriptionOutput;
import com.orbitra.application.usecase.SubscriptionUseCase;
import com.orbitra.presentation.dto.CreateSubscriptionRequest;
import com.orbitra.presentation.dto.UpgradeSubscriptionRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionUseCase subscriptionUseCase;

    public SubscriptionController(SubscriptionUseCase subscriptionUseCase) {
        this.subscriptionUseCase = subscriptionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionOutput create(@RequestBody @Valid CreateSubscriptionRequest request) {
        return subscriptionUseCase.create(request.planId(), request.startDate());
    }

    @GetMapping
    public List<SubscriptionOutput> listAll() {
        return subscriptionUseCase.listAll();
    }

    @GetMapping("/{id}")
    public SubscriptionOutput findById(@PathVariable UUID id) {
        return subscriptionUseCase.findById(id);
    }

    @PatchMapping("/{id}/cancel")
    public SubscriptionOutput cancel(@PathVariable UUID id) {
        return subscriptionUseCase.cancel(id);
    }

    @PatchMapping("/{id}/past-due")
    public SubscriptionOutput markPastDue(@PathVariable UUID id) {
        return subscriptionUseCase.markPastDue(id);
    }

    @PatchMapping("/{id}/payment")
    public SubscriptionOutput registerPayment(@PathVariable UUID id) {
        return subscriptionUseCase.registerPayment(id);
    }

    @PatchMapping("/{id}/upgrade")
    public SubscriptionOutput upgrade(@PathVariable UUID id, @RequestBody @Valid UpgradeSubscriptionRequest request) {
        return subscriptionUseCase.upgrade(id, request.planId());
    }
}
