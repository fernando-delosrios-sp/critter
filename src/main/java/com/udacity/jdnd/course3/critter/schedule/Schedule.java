package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public @Data class Schedule {
    @Id
    @GeneratedValue
    private long id;
    @ManyToMany
    private List<Long> employeeIds;
    @ManyToMany
    private List<Long> petIds;
    private LocalDate date;
    @ElementCollection
    private Set<EmployeeSkill> activities;
}
