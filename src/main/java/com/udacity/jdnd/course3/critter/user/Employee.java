package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
public @Data class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ElementCollection
    private Set<EmployeeSkill> skills;
    private Set<DayOfWeek> daysAvailable;
}
