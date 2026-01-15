package com.example.factory.events.service;

import com.example.factory.events.model.*;
import com.example.factory.events.repository.EventRejectionRepository;
import com.example.factory.events.repository.MachineEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventIngestionServiceImpl implements EventIngestionService{
    private final MachineEventRepository eventRepository;
    private final EventRejectionRepository rejectionRepository;
    
    @Override
    @Transactional
    public EventBatchResponseDTO ingestBatch(List<EventIngestRequestDTO> events){
        int accepted = 0, deduped = 0, updated= 0, rejected = 0;
        List<EventRejectionDTO> rejectionDTOS = new ArrayList<>();
        
        for(EventIngestRequestDTO dto: events){
            // validation
            Optional<String> validationError = validate(dto);
            if(validationError.isPresent()){
                rejected++;
                rejectionDTOS.add(new EventRejectionDTO(dto.eventId(), validationError.get()));
                rejectionRepository.save(EventRejection.from(dto.eventId(), validationError.get()));
                continue;
            }

            // Deduplication with DB lock
            Optional<MachineEvent> existing = eventRepository.findByEventIdForUpdate(dto.eventId());
            Instant now = Instant.now();
            if(existing.isEmpty()){
                // new Event
                eventRepository.save(toEntity(dto, now));
                accepted++;
                continue;
            }

            MachineEvent current = existing.get();

            //same payload -> dedupe
            if (isSamePayload(current, dto)){
                deduped++;
                continue;
            }

            //Newer update -> update
            if(now.isAfter(current.getReceivedTime())){
                updateEntity(current, dto, now);
                updated++;
            }else{
                deduped++;
            }
        }
        return new EventBatchResponseDTO(
                accepted, deduped, updated, rejected, rejectionDTOS
        );
    }

    private void updateEntity(MachineEvent current,
                              EventIngestRequestDTO dto,
                              Instant receivedTime) {

        current.setMachineId(dto.machineId());
        current.setFactoryId(dto.factoryId());
        current.setLineId(dto.lineId());
        current.setEventTime(dto.eventTime());
        current.setReceivedTime(receivedTime);
        current.setDurationMs(dto.durationMs());
        current.setDefectCount(dto.defectCount());

        eventRepository.save(current);
    }

    private boolean isSamePayload(MachineEvent e, EventIngestRequestDTO dto) {
        return Objects.equals(e.getMachineId(), dto.machineId())
                && Objects.equals(e.getFactoryId(), dto.factoryId())
                && Objects.equals(e.getLineId(), dto.lineId())
                && e.getDurationMs() == dto.durationMs()
                && e.getDefectCount() == dto.defectCount()
                && Objects.equals(e.getEventTime(), dto.eventTime());
    }

    private MachineEvent toEntity(EventIngestRequestDTO dto, Instant now) {
        MachineEvent e = new MachineEvent();
        e.setEventId(dto.eventId());
        e.setMachineId(dto.machineId());
        e.setFactoryId(dto.factoryId());
        e.setLineId(dto.lineId());
        e.setEventTime(dto.eventTime());
        e.setReceivedTime(now);
        e.setDurationMs(dto.durationMs());
        e.setDefectCount(dto.defectCount());
        return e;
    }

    private Optional<String> validate(EventIngestRequestDTO dto) {
        if(dto.durationMs() <0 || dto.durationMs() > 21600000){
            return Optional.of("INVALID_DURATION");
        }
        if(dto.eventTime().isAfter(Instant.now().plusSeconds(900))){
            return Optional.of("FUTURE_EVENT_TIME");
        }
        return Optional.empty();
    }
}
