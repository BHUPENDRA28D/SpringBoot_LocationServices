package com.example.SpringBoot_LocaitonSevices.Services;

import com.example.SpringBoot_LocaitonSevices.DTO.DriverLocationDTO;

import java.util.List;

public interface LocationServices {

    Boolean saveDriverLocation(String driverId,Double latitude,Double longitude);

    List<DriverLocationDTO> getNearByDrivers(Double latitude,Double longitude);
}
