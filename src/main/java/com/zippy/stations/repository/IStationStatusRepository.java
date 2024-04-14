package com.zippy.stations.repository;

import com.zippy.stations.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IStationStatusRepository extends JpaRepository<Station, Long> {
}
