package com.example.factory.events.repository;

import com.example.factory.events.model.MachineEvent;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface MachineEventRepository
        extends JpaRepository<MachineEvent, Long> {

    // 1️⃣ Basic lookup by eventId
    Optional<MachineEvent> findByEventId(String eventId);

    // 2️⃣ Lookup with DB-level lock (CRITICAL)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM MachineEvent e WHERE e.eventId = :eventId")
    Optional<MachineEvent> findByEventIdForUpdate(
            @Param("eventId") String eventId
    );

    // 3️⃣ Stats for a single machine
    @Query("""
        SELECT COUNT(e),
               COALESCE(
                   SUM(CASE WHEN e.defectCount >= 0
                            THEN e.defectCount ELSE 0 END), 0
               )
        FROM MachineEvent e
        WHERE e.machineId = :machineId
          AND e.eventTime >= :start
          AND e.eventTime < :end
    """)
    Object[] fetchMachineStats(
            @Param("machineId") String machineId,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    // 4️⃣ Top defect lines for a factory
    @Query("""
        SELECT e.lineId,
               SUM(CASE WHEN e.defectCount >= 0
                        THEN e.defectCount ELSE 0 END),
               COUNT(e)
        FROM MachineEvent e
        WHERE e.factoryId = :factoryId
          AND e.eventTime >= :from
          AND e.eventTime < :to
        GROUP BY e.lineId
        ORDER BY SUM(e.defectCount) DESC
    """)
    List<Object[]> findTopDefectLines(
            @Param("factoryId") String factoryId,
            @Param("from") Instant from,
            @Param("to") Instant to,
            Pageable pageable
    );
}
