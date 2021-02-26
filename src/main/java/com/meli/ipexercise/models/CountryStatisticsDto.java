package com.meli.ipexercise.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryStatisticsDto {

    private String country;
    private Long distance;
    private String distanceMeasure;
    private int invocationsQuantity;

}
