package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //@Query(value = "select c.* from customer c inner join pet p on c.id = p.owner_id where p.id = ?1", nativeQuery = true)
    @Query("select c from Customer c inner join c.pets p where p.id = ?1")
    Customer findOwnerByPet(Long id);
}