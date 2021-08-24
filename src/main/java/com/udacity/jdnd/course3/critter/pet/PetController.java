package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetRepository petRepository;

    @PostMapping
    @Transactional
    public Pet savePet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @GetMapping("/{petId}")
    public Pet getPet(@PathVariable long petId) {
        return petRepository.findById(petId).get();
    }

    @GetMapping
    public List<Pet> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<Pet> getPetsByOwner(@PathVariable long ownerId) {
        return petRepository.findByOwner_Id(ownerId);
    }
}
