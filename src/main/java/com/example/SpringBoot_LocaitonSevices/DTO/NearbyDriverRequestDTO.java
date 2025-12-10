package com.example.SpringBoot_LocaitonSevices.DTO;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearbyDriverRequestDTO {

        double latitude;
        double longitude;

}
