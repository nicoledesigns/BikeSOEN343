package com.soen343.tbd.application.dto;

import com.soen343.tbd.domain.model.Bike;
import com.soen343.tbd.domain.model.ids.DockId;

// might not use it
// deletable

public class DropOffRequestDto {
    private Bike bike;
    private DockId dockId;

    // Default constructor for deserialization
    public DropOffRequestDto() {}

    public DropOffRequestDto(Bike bike, DockId dockId) {
        this.bike = bike;
        this.dockId = dockId;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

}
