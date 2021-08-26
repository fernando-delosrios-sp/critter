package com.udacity.jdnd.course3.critter.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select e.id, e.name from employee e inner join employee_days_available d on e.id = d.employee_id inner join employee_skills s on e.id = s.employee_id where d.day = :day and s.skill in (:skills) group by e.id, e.name having count(e.id) = (select count(distinct skill) from employee_skills where skill in (:skills))", nativeQuery = true)
    List<Employee> findEmployeesForService(@Param("day") String day, @Param("skills") List<String> skills);
}
