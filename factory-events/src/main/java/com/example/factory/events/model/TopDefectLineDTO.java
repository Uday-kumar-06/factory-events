package com.example.factory.events.model;


//GET /stats/top-defect-lines
public record TopDefectLineDTO(String lineId, long totalDefects, long eventCount, double defectsPercent) {
}
