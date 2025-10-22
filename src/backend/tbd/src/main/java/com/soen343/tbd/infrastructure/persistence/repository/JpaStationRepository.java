package com.soen343.tbd.infrastructure.persistence.repository;

import com.soen343.tbd.infrastructure.persistence.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStationRepository extends JpaRepository<StationEntity, Long> {
}

/*
 >> asked chatgpt, not much i can see

 automatically writes functions aka
 findAll, findById(id), save(entity), deleteById(id);
 does the actual calls to database?
 do i need to make other functions?
 "" and i quote
 If want to add custom queries, can extend this interface with:
 ...List<StationEntity> findByStationStatus(StationStatus status)
 ...Optional<StationEntity> findByStationName(String name)
 Spring will automatically implement em based on the method name
 ""
 */