package com.soen343.tbd.application.service;

import com.soen343.tbd.application.dto.billing.FlexMoneyDTO;
import com.soen343.tbd.domain.model.Station;
import com.soen343.tbd.domain.model.ids.UserId;
import com.soen343.tbd.domain.model.user.User;
import com.soen343.tbd.domain.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*  1 flex money = 1 cent, so 100 flex money = 1$
    - checks if user is eligible for flex money obtention
    - can add flex money into user's balance
   >> todo: can automatically reduce bill of next trip using existing balance
 */

@Service
public class FlexMoneyService {

    private static final Logger logger = LoggerFactory.getLogger(FlexMoneyService.class);

    private final UserRepository userRepository;

    public FlexMoneyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addFlexMoneyIfEligible(UserId userId, Station station) {
        // bikes is count minus one since checking if it was
        // at <25% full before the docking of the bike return
        int bikes = station.getNumberOfBikesDocked() - 1;
        int capacity = station.getCapacity();
        double fullness = (double) bikes / capacity;

        if (fullness < 0.25) {
            Integer amount = 100;
            addFlexMoney(userId, amount);
        }
    }

    @Transactional
    public FlexMoneyDTO addFlexMoney(UserId userId, Integer amountToAdd) {

        logger.info("User: {}, add flex money: {}", userId.value(), amountToAdd);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId.value()));
        
        Integer currentBalance = user.getFlexMoney();
        Integer updatedBalance = currentBalance + amountToAdd;
        user.setFlexMoney(updatedBalance);
        userRepository.save(user);

        logger.info("User: {} flex money updated: {}", userId.value(), updatedBalance);

        return new FlexMoneyDTO(updatedBalance, amountToAdd, 0.0);
    }


}