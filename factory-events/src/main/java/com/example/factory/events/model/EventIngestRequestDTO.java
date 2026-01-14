package com.example.factory.events.model;

import java.time.Instant;

public record EventIngestRequestDTO(
        String eventId,String machineId, String factoryId,
        String lineId, Instant eventTime, long durationMs,
        int defectCount
) {}
