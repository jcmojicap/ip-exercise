package com.meli.ipexercise.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse extends BasicResponse {

    private String ip;
    private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime date;
    private String country;
    private String isoCode;
    private List<String> officialLanguages;
    private @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss z")
    List<ZonedDateTime> times;
    private String currency;
    private String estimatedDistance;
    private String distanceMeasure;

}
