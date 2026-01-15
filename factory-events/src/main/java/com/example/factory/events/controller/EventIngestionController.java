package com.example.factory.events.controller;

import com.example.factory.events.model.EventBatchResponseDTO;
import com.example.factory.events.model.EventIngestRequestDTO;
import com.example.factory.events.service.EventIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventIngestionController {

    private final EventIngestionService ingestionService;

    @PostMapping("/batch")
    public ResponseEntity<EventBatchResponseDTO> ingestBatch(
            @RequestBody List<EventIngestRequestDTO> events) {

        EventBatchResponseDTO response =
                ingestionService.ingestBatch(events);

        return ResponseEntity.ok(response);
    }
}

