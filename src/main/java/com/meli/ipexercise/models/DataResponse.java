package com.meli.ipexercise.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DataResponse {

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


}
