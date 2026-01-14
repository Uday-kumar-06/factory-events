package com.example.factory.events.repository;

import com.example.factory.events.model.EventRejection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRejectionRepository
        extends JpaRepository<EventRejection, Long> {
}
