package com.example.factory.events.controller;

import com.example.factory.events.model.StatsResponseDTO;
import com.example.factory.events.model.TopDefectLineDTO;
import com.example.factory.events.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<StatsResponseDTO> getStats(
            @RequestParam String machineId,
            @RequestParam Instant start,
            @RequestParam Instant end) {

        return ResponseEntity.ok(
                statsService.getMachineStats(machineId, start, end)
        );
    }

    @GetMapping("/top-defect-lines")
    public ResponseEntity<List<TopDefectLineDTO>> topDefectLines(
            @RequestParam String factoryId,
            @RequestParam Instant from,
            @RequestParam Instant to,
            @RequestParam(defaultValue = "10") int limit) {

        return ResponseEntity.ok(
                statsService.getTopDefectLines(factoryId, from, to, limit)
        );
    }
}

