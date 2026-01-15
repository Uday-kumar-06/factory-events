package com.example.factory.events.service;


import com.example.factory.events.model.StatsResponseDTO;
import com.example.factory.events.model.TopDefectLineDTO;
import com.example.factory.events.repository.MachineEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService{
    private final MachineEventRepository repository;

    @Override
    public StatsResponseDTO getMachineStats(String machineId, Instant start, Instant end){
        Object [] result = repository.fetchMachineStats(machineId, start, end);

        long eventsCount = (long) result[0];
        long defectsCount = (long) result[1];

        double windowHours = Duration.between(start, end).toSeconds() / 3600.0;

        double avgDefectRate = windowHours == 0 ? 0 : defectsCount / windowHours;

        String status = avgDefectRate < 2.0 ? "Healthy" : "Warning";

        return new StatsResponseDTO(
                machineId, start, end,
                eventsCount, defectsCount,
                avgDefectRate, status
        );
    }

    @Override
    public List<TopDefectLineDTO> getTopDefectLines(String factoryId, Instant from, Instant to, int limit){
        return repository.findTopDefectLines(
                        factoryId, from, to, PageRequest.of(0, limit)
                )
                .stream()
                .map(r -> {
                    String lineId = (String) r[0];
                    long defects = (long) r[1];
                    long events = (long) r[2];
                    double percent =
                            events == 0 ? 0 : (defects * 100.0 / events);

                    return new TopDefectLineDTO(
                            lineId, defects, events,
                            Math.round(percent * 100.0) / 100.0
                    );
                })
                .toList();
    }
}
