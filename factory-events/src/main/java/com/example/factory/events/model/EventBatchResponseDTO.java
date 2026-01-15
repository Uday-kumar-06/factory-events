package com.example.factory.events.model;

import java.util.List;

public record EventBatchResponseDTO(int accepted, int deduped, int updated, int rejected, List<EventRejectionDTO> rejections) {
}
