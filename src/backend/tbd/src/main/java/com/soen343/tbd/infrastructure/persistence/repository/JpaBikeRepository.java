package com.soen343.tbd.infrastructure.persistence.repository;

import com.soen343.tbd.infrastructure.persistence.entity.BikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBikeRepository extends JpaRepository<BikeEntity, Long> {
    Optional<BikeEntity> findByDock_DockId(Long dockId);
}
