package com.example.loop.repository;

import com.example.loop.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findByName(String name);;
    Optional<Reminder> findByDateTime(LocalDate date);
}
