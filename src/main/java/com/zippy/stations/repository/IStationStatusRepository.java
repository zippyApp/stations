package com.zippy.stations.repository;

import com.zippy.stations.model.StationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface IStationStatusRepository extends JpaRepository<StationStatus, Long> {
    @Transactional
    Optional<StationStatus> getStationStatusById(Long id);
}
