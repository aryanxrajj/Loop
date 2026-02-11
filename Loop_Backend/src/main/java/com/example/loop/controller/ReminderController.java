package com.example.loop.controller;

import com.example.loop.model.Reminder;
import com.example.loop.service.ReminderService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reminder")
@CrossOrigin(value = "*")
public class ReminderController {
    private ReminderService reminderService;

    public ReminderController (ReminderService reminderService){
        this.reminderService = reminderService;
    }

    @GetMapping
    public List<Reminder> getAllReminder(){
        return reminderService.getAllReminder();
    }

    @GetMapping("/{date}")
    public Optional<Reminder> getByDateTime(@PathVariable LocalDate date){
        return reminderService.getByDateTime(date);
    }

    @PostMapping
    public Reminder createTopic(@RequestBody Reminder reminder){
        return reminderService.createReminder(reminder);
    }
}
