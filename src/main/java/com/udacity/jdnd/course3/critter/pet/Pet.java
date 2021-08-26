package com.udacity.jdnd.course3.critter.pet;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.udacity.jdnd.course3.critter.user.Customer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public @Data class Pet {
    @Id
    @GeneratedValue
    private long id;

    @EqualsAndHashCode.Include
    private PetType type;

    @EqualsAndHashCode.Include
    private String name;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    private Customer owner;

    private LocalDate birthDate;

    private String notes;
}
