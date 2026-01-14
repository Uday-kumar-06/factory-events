package com.example.factory.events.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "event_rejections")
public class EventRejection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;

    private String reason; // INVALID_DURATION, FUTURE_EVENT_TIME, etc.

    private Instant rejectedAt;
}
