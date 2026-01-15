package com.example.factory.events.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "machine_events",
        uniqueConstraints = @UniqueConstraint(columnNames = "event_id")
)
public class MachineEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "machine_id", nullable = false)
    private String machineId;

    @Column(name = "factory_id")
    private String factoryId;   // needed for top-defect-lines

    @Column(name = "line_id")
    private String lineId;      // needed for top-defect-lines

    @Column(name = "event_time", nullable = false)
    private Instant eventTime;

    @Column(name = "received_time", nullable = false)
    private Instant receivedTime;

    @Column(name = "duration_ms", nullable = false)
    private long durationMs;

    @Column(name = "defect_count", nullable = false)
    private int defectCount;
}
