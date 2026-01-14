package com.example.factory.events.model;

import java.util.List;

public record EventBatchResponseDTO(int accepted, int deduped, int updated, List<EventRejectionDTO> rejections) {
}
