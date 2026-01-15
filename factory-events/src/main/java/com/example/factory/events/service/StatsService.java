package com.example.factory.events.service;

import com.example.factory.events.model.StatsResponseDTO;
import com.example.factory.events.model.TopDefectLineDTO;

import java.time.Instant;
import java.util.List;

public interface StatsService {
    StatsResponseDTO getMachineStats(
            String machineId, Instant start, Instant end
    );

    List<TopDefectLineDTO> getTopDefectLines(
            String factoryId, Instant from, Instant to, int limit
    );
}
