package com.meli.ipexercise.services.impl;

import com.meli.ipexercise.models.CountryCurrency;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.retrofit.RetrofitClient;
import com.meli.ipexercise.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private Endpoints endpoints;
    private ExchangeRate exchangeRate;
    @Value("${http-services.endpoints.EXCHANGE_INFO.access_key}")
    private String accessKey;
    private RetrofitClient retrofitClient = new RetrofitClient();

    public ExchangeRateServiceImpl(@Value("${http-services.endpoints.EXCHANGE_INFO.host}") String url){
        endpoints = retrofitClient.createRetrofitClient(url);
    }

    @Override
    public ExchangeRate getExchangeRate(List<CountryCurrency> currencies) throws IOException {
        exchangeRate = endpoints.getExchangeRates(accessKey).execute().body();
        return exchangeRate;
    }
}
