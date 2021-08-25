package com.udacity.jdnd.course3.critter.pet;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.view.Views;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public @Data class Pet {
    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private long id;

    @JsonView(Views.Public.class)
    @EqualsAndHashCode.Include
    private PetType type;

    @JsonView(Views.Public.class)
    @EqualsAndHashCode.Include
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    private Customer owner;

    @JsonView(Views.Public.class)
    public long getOwnerId() {
        return this.getOwner().getId();
    }

    @JsonView(Views.Public.class)
    private LocalDate birthDate;

    @JsonView(Views.Public.class)
    private String notes;
}
