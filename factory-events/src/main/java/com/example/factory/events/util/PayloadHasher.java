package com.example.factory.events.util;

import com.example.factory.events.model.EventIngestRequestDTO;

import java.util.Objects;

public final class PayloadHasher {

    private PayloadHasher() {
        // utility class
    }

    public static long hash(EventIngestRequestDTO dto) {
        return Objects.hash(
                dto.eventId(),
                dto.machineId(),
                dto.factoryId(),
                dto.lineId(),
                dto.eventTime(),
                dto.durationMs(),
                dto.defectCount()
        );
    }
}
