package com.udacity.jdnd.course3.critter.user;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.view.Views;

import lombok.Data;

public @Data class EmployeeRequest {
    @JsonView(Views.Public.class)
    private Set<EmployeeSkill> skills;

    @JsonView(Views.Public.class)
    private LocalDate date;
}
