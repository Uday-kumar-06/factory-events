package com.example.factory.events.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "event_rejections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRejection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventId;

    @Column(nullable = false)
    private String reason; // INVALID_DURATION, FUTURE_EVENT_TIME, etc.

    @Column(nullable = false)
    private Instant rejectedAt;

    // ✅ This matches your service call: EventRejection.from(...)
    public static EventRejection from(String eventId, String reason) {
        return EventRejection.builder()
                .eventId(eventId)
                .reason(reason)
                .rejectedAt(Instant.now())
                .build();
    }
}
