package com.example.SpringBoot_LocaitonSevices.Services;

import com.example.SpringBoot_LocaitonSevices.DTO.DriverLocationDTO;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RedisLocationFinderServiceImpl implements LocationServices {


    private static  final String DRIVER_OPS_KEY ="drivers";
    private static  final Double SEARCH_RADIUS = 5.0;


    private StringRedisTemplate stringRedisTemplate;

    public RedisLocationFinderServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double longitude) {


            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            geoOps.add(DRIVER_OPS_KEY, new RedisGeoCommands.GeoLocation<>(
                    String.valueOf(driverId),
                    new Point(longitude, latitude)
            ));
            return true;


    }

    @Override
    public List<DriverLocationDTO> getNearByDrivers(Double latitude, Double longitude) {
        GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
        Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
        Circle within = new Circle(new Point(longitude, latitude), radius);


        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_OPS_KEY, within);

        List<DriverLocationDTO> drivers = new ArrayList<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            DriverLocationDTO driverLocationDTO = DriverLocationDTO.builder()
                    .driverId(result.getContent().getName())
                    .latitude(result.getContent().getPoint().getY())
                    .longitude(result.getContent().getPoint().getX())
                    .build();
            drivers.add(driverLocationDTO);

        }

        return  drivers;
    }
}
