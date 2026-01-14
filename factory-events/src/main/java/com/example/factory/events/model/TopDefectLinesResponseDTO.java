package com.example.factory.events.model;

import java.time.Instant;
import java.util.List;

public record TopDefectLinesResponseDTO(String factoryId, Instant from, Instant to, List<TopDefectLineDTO> lines) {
}
