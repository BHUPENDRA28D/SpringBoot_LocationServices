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
    private static  final Double SEARCH_RADIUS = 2.00;


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

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                geoOps.radius(DRIVER_OPS_KEY, within);

        List<DriverLocationDTO> drivers = new ArrayList<>();

        if (results == null || results.getContent() == null) {
            return drivers;
        }

        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {

            if (result == null || result.getContent() == null) {
                continue;
            }

            List<Point> positions =
                    geoOps.position(DRIVER_OPS_KEY, result.getContent().getName());

            if (positions == null || positions.isEmpty() || positions.get(0) == null) {
                continue;
            }

            Point point = positions.get(0);

            drivers.add(
                    DriverLocationDTO.builder()
                            .driverId(result.getContent().getName())
                            .latitude(point.getY())
                            .longitude(point.getX())
                            .build()
            );
        }

        return drivers;
    }




}
