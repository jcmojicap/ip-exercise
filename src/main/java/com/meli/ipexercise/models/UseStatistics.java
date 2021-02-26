package com.meli.ipexercise.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UseStatistics extends BasicResponse {

    private CountryStatisticsDto nearestCountry;
    private CountryStatisticsDto fartherCountry;
    private String average;

}
