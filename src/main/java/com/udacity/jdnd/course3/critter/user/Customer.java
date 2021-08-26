package com.udacity.jdnd.course3.critter.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.udacity.jdnd.course3.critter.pet.Pet;

import org.assertj.core.util.Sets;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public @Data class Customer {
    @Id
    @GeneratedValue
    private long id;

    @EqualsAndHashCode.Include
    private String name;

    private String phoneNumber;

    private String notes;
    
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Pet> pets;

    public void addPet(Pet pet) {
        if(pets == null){
           pets = Sets.newHashSet();
        }
        pets.add(pet);
    }
}
