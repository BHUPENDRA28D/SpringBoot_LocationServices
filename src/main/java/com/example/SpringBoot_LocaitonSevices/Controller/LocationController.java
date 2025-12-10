package com.example.SpringBoot_LocaitonSevices.Controller;

import com.example.SpringBoot_LocaitonSevices.DTO.NearbyDriverRequestDTO;
import com.example.SpringBoot_LocaitonSevices.DTO.SaveDriverLocationRequestDTO;
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

    private StringRedisTemplate stringRedisTemplate;
    private static  final String DRIVER_OPS_KEY ="drivers";
    private static  final Double SEARCH_RADIUS = 100.0;

    public LocationController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

/*
*  POSTMAPPED --- Saving Driver Locaiton
*/

    @PostMapping("/driver")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationRequestDTO saveDriverLocationRequestDTO) {

        try {

            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            geoOps.add(DRIVER_OPS_KEY, new RedisGeoCommands.GeoLocation<>(
                    String.valueOf(saveDriverLocationRequestDTO.getDriverId()),
                    new Point(saveDriverLocationRequestDTO.getLongitude(), saveDriverLocationRequestDTO.getLatitude())
            ));




            return new ResponseEntity<>(true, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /*
     *  GETMAPPED --- geting near by Driver
     */
    @GetMapping("/nearby/drivers")
    public ResponseEntity<List<String>> getNearByDriverS(@RequestBody NearbyDriverRequestDTO nearByDriversRequestDTO) {

        try {
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
            Circle within = new Circle(new Point(nearByDriversRequestDTO.getLatitude(), nearByDriversRequestDTO.getLongitude()), radius);

            GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_OPS_KEY, within);

            List<String> drivers = new ArrayList<>();
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                drivers.add(result.getContent().getName());
            }

            return new ResponseEntity<>(drivers, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
