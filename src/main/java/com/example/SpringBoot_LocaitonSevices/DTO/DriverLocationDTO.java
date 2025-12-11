package com.example.SpringBoot_LocaitonSevices.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverLocationDTO {

    String driverId;
    Double latitude;
    Double longitude;
}
