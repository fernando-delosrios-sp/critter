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
    private PetService petService;

    @PostMapping
    @Transactional
    public Pet savePet(@RequestBody Pet pet) {
        return petService.savePet(pet);
    }

    @GetMapping("/{petId}")
    public Pet getPet(@PathVariable long petId) {
        return petService.getPetById(petId);
    }

    @GetMapping
    public List<Pet> getPets(){
        return petService.getPets();
    }

    @GetMapping("/owner/{ownerId}")
    public List<Pet> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByOwnerId(ownerId);
    }
}
