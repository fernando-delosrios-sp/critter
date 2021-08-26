package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPetId(long petId) {
        return scheduleRepository.findScheduleByPets_Id(petId);
    }

    public List<Schedule> getScheduleForCustomerId(long customerId) {
        return scheduleRepository.findScheduleByPets_Owner_Id(customerId);
    }

    public List<Schedule> getScheduleForEmployeeId(long employeeId) {
        return scheduleRepository.findScheduleByEmployees_Id(employeeId);
    }
}
