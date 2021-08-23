package com.udacity.jdnd.course3.critter.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.view.Views;

import lombok.Data;

@Entity
public @Data class Customer {
    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private long id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String phoneNumber;

    @JsonView(Views.Public.class)
    private String notes;

    @JsonView(Views.Public.class)
    public List<Long> getPetsIds() {
        return this.getPets().stream().map(Pet::getId).collect(Collectors.toList());
    }
      
    @OneToMany(mappedBy="owner")
    private Set<Pet> pets;
}
