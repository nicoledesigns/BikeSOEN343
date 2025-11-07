package com.soen343.tbd.infrastructure.persistence.repository;

import com.soen343.tbd.infrastructure.persistence.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBillRepository extends JpaRepository<BillEntity, Long> {

    List<BillEntity> findAllByUser_UserId(Long userId);
}
