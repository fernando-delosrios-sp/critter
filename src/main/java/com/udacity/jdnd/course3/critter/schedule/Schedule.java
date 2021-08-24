package com.udacity.jdnd.course3.critter.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.view.Views;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public @Data class Schedule {
    @Id
    @GeneratedValue
    private long id;

    public Schedule(long id) {
      this.id = id;
    }

    @ManyToMany
    @JoinTable(
      name = "schedule_employees",
      joinColumns = { @JoinColumn(name = "schedule_id")},
      inverseJoinColumns = { @JoinColumn(name = "employee_id")}
    )
    @EqualsAndHashCode.Include
    private Set<Employee> employees;

    @JsonView(Views.Public.class)
    public List<Long> getEmployeeIds() {
      return this.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
    }

    @ManyToMany
    @JoinTable(
      name = "schedule_pets",
      joinColumns = { @JoinColumn(name = "schedule_id")},
      inverseJoinColumns = { @JoinColumn(name = "pet_id")}
    )
    @EqualsAndHashCode.Include
    private Set<Pet> pets;

    @JsonView(Views.Public.class)
    public List<Long> getPetsIds() {
      return this.getPets().stream().map(Pet::getId).collect(Collectors.toList());
    }

    @JsonView(Views.Public.class)
    @EqualsAndHashCode.Include
    private LocalDate date;

    @ElementCollection
    @Column(name = "activity")
    @JsonView(Views.Public.class)
    @EqualsAndHashCode.Include
    private Set<EmployeeSkill> activities;
}
