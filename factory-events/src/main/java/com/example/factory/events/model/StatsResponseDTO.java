package com.example.factory.events.model;

import java.time.Instant;
// String status is for--Healthy/Warning
//GET /stats?machineId=...&start=...&end=...
public record StatsResponseDTO(String machineId, Instant start, Instant end, long eventCount, long defectCount, double avgDefectRate, String status) {
}
