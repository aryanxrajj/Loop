package com.example.loop.service;

import com.example.loop.model.Reminder;
import com.example.loop.repository.ReminderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReminderService {
    private ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository){
        this.reminderRepository = reminderRepository;
    }

    public List<Reminder> getAllReminder(){
        return reminderRepository.findAll();
    }

    public Optional<Reminder> getByDateTime(LocalDate dateTime){
        return reminderRepository.findByDateTime(dateTime);
    }

    public Reminder createReminder(Reminder reminder){
        return reminderRepository.save(reminder);
    }
}
