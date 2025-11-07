package com.soen343.tbd.domain.repository;

import java.util.List;
import java.util.Optional;

import com.soen343.tbd.domain.model.Bill;
import com.soen343.tbd.domain.model.ids.BillId;
import com.soen343.tbd.domain.model.ids.UserId;

public interface BillRepository {
    Optional<Bill> findById(BillId billId);

    Bill save(Bill bill);

    List<Bill> findAllByUserId(UserId userId);

    List<Bill> findAll();
}
