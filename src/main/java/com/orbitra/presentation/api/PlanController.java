package com.orbitra.presentation.api;

import com.orbitra.application.dto.PlanOutput;
import com.orbitra.application.usecase.PlanUseCase;
import com.orbitra.presentation.dto.CreatePlanRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanUseCase planUseCase;

    public PlanController(PlanUseCase planUseCase) {
        this.planUseCase = planUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanOutput create(@RequestBody @Valid CreatePlanRequest request) {
        return planUseCase.create(request.name(), request.price());
    }

    @GetMapping
    public List<PlanOutput> listAll() {
        return planUseCase.listAll();
    }
}
