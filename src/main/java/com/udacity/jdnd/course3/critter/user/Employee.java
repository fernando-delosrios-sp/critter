package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.view.Views;

import lombok.Data;

@Entity
public @Data class Employee {
    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private long id;

    @JsonView(Views.Public.class)
    private String name;

    @ElementCollection
    @Column(name = "skill")
    @JsonView(Views.Public.class)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Column(name = "day")
    @JsonView(Views.Public.class)
    private Set<DayOfWeek> daysAvailable;
}
