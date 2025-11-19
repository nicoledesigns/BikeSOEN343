package com.soen343.tbd.application.dto.billing;

public class FlexMoneyDTO {
    private Integer currentBalance; // current amount of fake money
    private Integer addMoney; // for adding more fake money
    private Double reduceBill; // bills are double, convert to partially pay them

    public FlexMoneyDTO(Integer currentBalance, Integer addMoney, Double reduceBill) {
        this.currentBalance = currentBalance;
        this.addMoney = addMoney;
        this.reduceBill = reduceBill;
    }

    /*******************
     
     Getters and setters
     
     ******************/

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Integer getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(Integer addMoney) {
        this.addMoney = addMoney;
    }

    public Double getReduceBill() {
        return reduceBill;
    }

    public void setReduceBill(Double reduceBill) {
        this.reduceBill = reduceBill;
    }
    
}
