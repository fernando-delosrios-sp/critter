package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public Customer saveCustomer(@RequestBody Customer customer){
        return userService.saveCustomer(customer);
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers(){
        return userService.getAllCustomers();        
    }

    @GetMapping("/customer/pet/{petId}")
    public Customer getOwnerByPet(@PathVariable long petId){
        return userService.getOwnerByPetId(petId);
    }

    @PostMapping("/employee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return userService.saveEmployee(employee);
    }

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable long employeeId) {
        return userService.getEmployeeById(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setAvailability(daysAvailable, employeeId);
    }

    @PostMapping("/employee/availability")
    public List<Employee> findEmployeesForService(@RequestBody EmployeeRequest employee) {
        return userService.findEmployeesForService(employee);
    }
}
