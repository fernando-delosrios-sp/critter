package com.udacity.jdnd.course3.critter.pet;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.udacity.jdnd.course3.critter.user.Customer;

import lombok.Data;

@Entity
public @Data class Pet {
    @Id
    @GeneratedValue
    private long id;
    private PetType type;
    private String name;
    @ManyToOne
    private Customer owner;
    private LocalDate birthDate;
    private String notes;
}
