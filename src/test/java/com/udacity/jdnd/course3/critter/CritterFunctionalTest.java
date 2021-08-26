package com.udacity.jdnd.course3.critter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetController;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleController;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequest;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.UserController;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a set of functional tests to validate the basic capabilities desired for this application.
 * Students will need to configure the application to run these tests by adding application.properties file
 * to the test/resources directory that specifies the datasource. It can run using an in-memory H2 instance
 * and should not try to re-use the same datasource used by the rest of the app.
 *
 * These tests should all pass once the project is complete.
 */
@EnableJpaAuditing
@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class CritterFunctionalTest {

    @Autowired
    private UserController userController;

    @Autowired
    private PetController petController;

    @Autowired
    private ScheduleController scheduleController;

    //Used to debug object serialisation online
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreateCustomer(){
        Customer customer = createCustomer();
        Customer newCustomer = userController.saveCustomer(customer);
        Customer retrievedCustomer = userController.getAllCustomers().get(0);
        Assertions.assertEquals(newCustomer, customer);
        Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
        Assertions.assertTrue(retrievedCustomer.getId() > 0);        
    }

    @Test
    public void testCreateEmployee(){
        Employee employee = createEmployee();
        Employee newEmployee = userController.saveEmployee(employee);
        Employee retrievedEmployee = userController.getEmployee(newEmployee.getId());
        Assertions.assertEquals(newEmployee, employee);
        Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
        Assertions.assertTrue(retrievedEmployee.getId() > 0);
    }

    @Test
    public void testAddPetsToCustomer() {
        Customer customer = createCustomer();
        Customer newCustomer = userController.saveCustomer(customer);

        Pet pet = createPet();
        pet.setOwner(newCustomer);
        Pet newPet = petController.savePet(pet);

        //make sure pet contains customer id
        Pet retrievedPet = petController.getPet(newPet.getId());
        Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
        Assertions.assertEquals(retrievedPet.getOwner().getId(), newCustomer.getId());

        //make sure you can retrieve pets by owner
        List<Pet> pets = petController.getPetsByOwner(newCustomer.getId());
        Assertions.assertEquals(newPet.getId(), pets.get(0).getId());
        Assertions.assertEquals(newPet.getName(), pets.get(0).getName());

        //check to make sure customer now also contains pet
        Customer retrievedCustomer = userController.getAllCustomers().get(0);
        Assertions.assertTrue(retrievedCustomer.getPets() != null && retrievedCustomer.getPets().size() > 0);
        Assertions.assertTrue(retrievedCustomer.getPets().contains(retrievedPet));
    }

    @Test
    public void testFindPetsByOwner() {
        Customer customer = createCustomer();
        Customer newCustomer = userController.saveCustomer(customer);

        Pet pet = createPet();
        pet.setOwner(newCustomer);
        Pet newPet = petController.savePet(pet);
        //If we're creating a new pet object we cannot reuse the same managed object (Spring Data JPA seems to start managing the reference object on save). Using a new one instead.
        pet = createPet();
        pet.setType(PetType.DOG);
        //Setting owner for the default assertion to work
        pet.setOwner(newCustomer);
        pet.setName("DogName");
        Pet newPet2 = petController.savePet(pet);

        List<Pet> pets = petController.getPetsByOwner(newCustomer.getId());
        Assertions.assertEquals(pets.size(), 2);
        Assertions.assertEquals(pets.get(0).getOwner().getId(), newCustomer.getId());
        Assertions.assertEquals(pets.get(0).getId(), newPet.getId());
    }

    @Test
    public void testFindOwnerByPet() {
        Customer customer = createCustomer();
        Customer newCustomer = userController.saveCustomer(customer);

        Pet pet = createPet();
        pet.setOwner(newCustomer);
        Pet newPet = petController.savePet(pet);

        Customer owner = userController.getOwnerByPet(newPet.getId());
        Assertions.assertEquals(owner.getId(), newCustomer.getId());
        Assertions.assertTrue(owner.getPets().contains(newPet));
    }

    @Test
    public void testChangeEmployeeAvailability() {
        Employee employee = createEmployee();
        Employee emp1 = userController.saveEmployee(employee);
        Assertions.assertNull(emp1.getDaysAvailable());

        Set<DayOfWeek> availability = Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
        userController.setAvailability(availability, emp1.getId());

        Employee emp2 = userController.getEmployee(emp1.getId());
        Assertions.assertEquals(availability, emp2.getDaysAvailable());
    }

    @Test
    public void testFindEmployeesByServiceAndTime() {
        Employee emp1 = createEmployee();
        Employee emp2 = createEmployee();
        Employee emp3 = createEmployee();

        emp1.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        emp2.setDaysAvailable(Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

        emp1.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        emp2.setSkills(Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.WALKING));
        emp3.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        Employee emp1n = userController.saveEmployee(emp1);
        Employee emp2n = userController.saveEmployee(emp2);
        Employee emp3n = userController.saveEmployee(emp3);

        //make a request that matches employee 1 or 2
        EmployeeRequest er1 = new EmployeeRequest();
        er1.setDate(LocalDate.of(2019, 12, 25)); //wednesday
        er1.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));

        Set<Long> eIds1 = userController.findEmployeesForService(er1).stream().map(Employee::getId).collect(Collectors.toSet());
        Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
        Assertions.assertEquals(eIds1, eIds1expected);

        //make a request that matches only employee 3
        EmployeeRequest er2 = new EmployeeRequest();
        er2.setDate(LocalDate.of(2019, 12, 27)); //friday
        er2.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

        Set<Long> eIds2 = userController.findEmployeesForService(er2).stream().map(Employee::getId).collect(Collectors.toSet());
        Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
        Assertions.assertEquals(eIds2, eIds2expected);
    }

    @Test
    public void testSchedulePetsForServiceWithEmployee() {
        Employee employeeTemp = createEmployee();
        employeeTemp.setDaysAvailable(Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        Employee employee = userController.saveEmployee(employeeTemp);
        Customer customer = userController.saveCustomer(createCustomer());
        Pet petTemp = createPet();
        petTemp.setOwner(customer);
        Pet pet = petController.savePet(petTemp);

        LocalDate date = LocalDate.of(2019, 12, 25);
        Set<Pet> pets = Sets.newHashSet(pet);
        Set<Employee> employees = Sets.newHashSet(employee);
        Set<EmployeeSkill> skillSet =  Sets.newHashSet(EmployeeSkill.PETTING);

        scheduleController.createSchedule(createSchedule(pets, employees, date, skillSet));
        Schedule schedule = scheduleController.getAllSchedules().get(0);

        Assertions.assertEquals(schedule.getActivities(), skillSet);
        Assertions.assertEquals(schedule.getDate(), date);
        Assertions.assertEquals(schedule.getEmployees(), employees);
        Assertions.assertEquals(schedule.getPets(), pets);
    }

    @Test
    public void testFindScheduleByEntities() {
        Schedule sched1 = populateSchedule(1, 2, LocalDate.of(2019, 12, 25), Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
        Schedule sched2 = populateSchedule(3, 1, LocalDate.of(2019, 12, 26), Sets.newHashSet(EmployeeSkill.PETTING));

        //add a third schedule that shares some employees and pets with the other schedules
        Schedule sched3 = new Schedule();
        sched3.setEmployees(sched1.getEmployees());
        sched3.setPets(sched2.getPets());
        sched3.setActivities(Sets.newHashSet(EmployeeSkill.SHAVING, EmployeeSkill.PETTING));
        sched3.setDate(LocalDate.of(2020, 3, 23));
        scheduleController.createSchedule(sched3);

        /*
            We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
            and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
            schedule 1, we should get both the first and third schedule as our result.
         */

        //Employee 1 in is both schedule 1 and 3
        List<Schedule> scheds1e = scheduleController.getScheduleForEmployee(sched1.getEmployees().iterator().next().getId());
        compareSchedules(sched1, scheds1e.get(0));
        compareSchedules(sched3, scheds1e.get(1));

        //Employee 2 is only in schedule 2
        List<Schedule> scheds2e = scheduleController.getScheduleForEmployee(sched2.getEmployees().iterator().next().getId());
        compareSchedules(sched2, scheds2e.get(0));

        //Pet 1 is only in schedule 1
        List<Schedule> scheds1p = scheduleController.getScheduleForPet(sched1.getPets().iterator().next().getId());
        compareSchedules(sched1, scheds1p.get(0));

        //Pet from schedule 2 is in both schedules 2 and 3
        List<Schedule> scheds2p = scheduleController.getScheduleForPet(sched2.getPets().iterator().next().getId());
        compareSchedules(sched2, scheds2p.get(0));
        compareSchedules(sched3, scheds2p.get(1));

        //Owner of the first pet will only be in schedule 1
        List<Schedule> scheds1c = scheduleController.getScheduleForCustomer((userController.getOwnerByPet(sched1.getPets().iterator().next().getId())).getId());
        compareSchedules(sched1, scheds1c.get(0));

        //Owner of pet from schedule 2 will be in both schedules 2 and 3
        List<Schedule> scheds2c = scheduleController.getScheduleForCustomer((userController.getOwnerByPet(sched2.getPets().iterator().next().getId()).getId()));
        compareSchedules(sched2, scheds2c.get(0));
        compareSchedules(sched3, scheds2c.get(1));
    }

    private static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setName("TestEmployee");
        employee.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
        return employee;
    }
    private static Customer createCustomer() {
        Customer customer = new Customer();
        customer.setName("TestEmployee");
        customer.setPhoneNumber("123-456-789");
        return customer;
    }

    private static Pet createPet() {
        Pet pet = new Pet();
        pet.setName("TestPet");
        pet.setType(PetType.CAT);
        return pet;
    }

    // private static EmployeeRequest createEmployeeRequest() {
    //     EmployeeRequest employeeRequest = new EmployeeRequest();
    //     employeeRequest.setDate(LocalDate.of(2019, 12, 25));
    //     employeeRequest.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
    //     return employeeRequest;
    // }

    private static Schedule createSchedule(Set<Pet> pets, Set<Employee> employees, LocalDate date, Set<EmployeeSkill> activities) {
        Schedule schedule = new Schedule();
        schedule.setPets(pets);
        schedule.setEmployees(employees);
        schedule.setDate(date);
        schedule.setActivities(activities);
        return schedule;
    }

    private Schedule populateSchedule(int numEmployees, int numPets, LocalDate date, Set<EmployeeSkill> activities) {
        Set<Employee> employees = IntStream.range(0, numEmployees)
                .mapToObj(i -> createEmployee())
                .map(e -> {
                    e.setSkills(activities);
                    e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                    return userController.saveEmployee(e);
                }).collect(Collectors.toSet());
        Customer cust = userController.saveCustomer(createCustomer());
        Set<Pet> pets = IntStream.range(0, numPets)
                .mapToObj(i -> createPet())
                .map(p -> {
                    p.setOwner(cust);
                    return petController.savePet(p);
                }).collect(Collectors.toSet());
        return scheduleController.createSchedule(createSchedule(pets, employees, date, activities));
    }

    private static void compareSchedules(Schedule sched1, Schedule sched2) {
        Assertions.assertEquals(sched1.getPets(), sched2.getPets());
        Assertions.assertEquals(sched1.getActivities(), sched2.getActivities());
        Assertions.assertEquals(sched1.getEmployees().iterator().next().getId(), sched2.getEmployees().iterator().next().getId());
        Assertions.assertEquals(sched1.getDate(), sched2.getDate());
    }

}
