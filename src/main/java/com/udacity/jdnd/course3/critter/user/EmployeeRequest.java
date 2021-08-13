package com.udacity.jdnd.course3.critter.user;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

public @Data class EmployeeRequest {
    private Set<EmployeeSkill> skills;
    private LocalDate date;
}
