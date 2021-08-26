package com.udacity.jdnd.course3.critter.pet;

import java.util.List;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PetService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;
    
    public Pet savePet(Pet pet) {
        Pet returnedPet = petRepository.save(pet);
        Customer customer = returnedPet.getOwner();
        if (customer != null) {
            customer.addPet(returnedPet);
            customerRepository.save(customer);
        }
        
        return returnedPet;
    }

    public Pet getPetById(long petId) {
        return petRepository.findById(petId).get();
    }

    public List<Pet> getPetsByOwnerId(long ownerId) {
        return petRepository.findByOwner_Id(ownerId);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }
    
}
