package com.meli.ipexercise.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ExchangeRate {

    private boolean success;
    private long timestamp;
    private String base;
    private @DateTimeFormat(pattern="yyyy-MM-dd") Date date;
    private Map<String, String> rates;

}
