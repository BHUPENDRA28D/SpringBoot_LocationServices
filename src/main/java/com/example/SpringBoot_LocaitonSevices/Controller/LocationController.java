package com.example.SpringBoot_LocaitonSevices.Controller;

import com.example.SpringBoot_LocaitonSevices.DTO.DriverLocationDTO;
import com.example.SpringBoot_LocaitonSevices.DTO.NearbyDriverRequestDTO;
import com.example.SpringBoot_LocaitonSevices.DTO.SaveDriverLocationRequestDTO;
import com.example.SpringBoot_LocaitonSevices.Services.LocationServices;
import com.google.gson.internal.GsonTypes;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {


    private final LocationServices locationServices;

    public LocationController(LocationServices locationServices) {
        this.locationServices = locationServices;
    }


    /*
*  POSTMAPPED --- Saving Driver Locaiton
*/

    @PostMapping("/driver")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationRequestDTO saveDriverLocationRequestDTO) {

        try{
            Boolean response = locationServices.saveDriverLocation(saveDriverLocationRequestDTO.getDriverId(), saveDriverLocationRequestDTO.getLatitude(), saveDriverLocationRequestDTO.getLongitude());

            return new ResponseEntity<>(response,HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false,HttpStatus.CREATED);

        }

    }


    /*
     *  GETMAPPED --- geting near by Driver
     */
    @PostMapping("/nearby/drivers")
    public ResponseEntity<List<DriverLocationDTO>> getNearByDriverS(@RequestBody NearbyDriverRequestDTO nearByDriversRequestDTO) {

        try {

           List<DriverLocationDTO> drivers = locationServices.getNearByDrivers(nearByDriversRequestDTO.getLatitude(), nearByDriversRequestDTO.getLongitude());

            return new ResponseEntity<>(drivers, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
