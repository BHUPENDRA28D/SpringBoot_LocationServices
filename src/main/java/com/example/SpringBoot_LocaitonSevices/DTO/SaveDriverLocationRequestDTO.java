package com.example.SpringBoot_LocaitonSevices.DTO;

import lombok.*;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveDriverLocationRequestDTO {

    String driverId;
    double latitude;
    double longitude;
}
