package com.example.SpringBoot_LocaitonSevices.DTO;

import lombok.*;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveDriverLocationRequestDTO {

    private   String driverId;
    private  Double latitude;
    private   Double longitude;
}
