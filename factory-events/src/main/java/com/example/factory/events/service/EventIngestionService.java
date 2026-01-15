package com.example.factory.events.service;

import com.example.factory.events.model.EventBatchResponseDTO;
import com.example.factory.events.model.EventIngestRequestDTO;

import java.util.List;

public interface EventIngestionService {
    EventBatchResponseDTO ingestBatch(List<EventIngestRequestDTO> events);
}
