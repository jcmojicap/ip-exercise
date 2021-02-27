package com.meli.ipexercise.services.impl;

import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.retrofit.RetrofitClient;
import com.meli.ipexercise.services.CountryBasicDataService;
import com.meli.ipexercise.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CountryBasicDataServiceImpl implements CountryBasicDataService {

    private RetrofitClient retrofitClient = new RetrofitClient();
    private Endpoints endpoints;

    private ExchangeRateService exchangeRateService;

    public CountryBasicDataServiceImpl(@Value("${http-services.endpoints.COUNTRY_INFO.host}") String url, ExchangeRateService exchangeRateService){
        endpoints = retrofitClient.createRetrofitClient(url);
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public CountryInfo getCountryBasicData(IpInfoDto ipInfoDto) throws IOException {
        if (ipInfoDto.getCountryCode3() == null || ipInfoDto.getCountryCode3().isEmpty()){
            return new CountryInfo();
        }
        CountryInfo countryInfo = endpoints.getCountryInfo(ipInfoDto.getCountryCode3()).execute().body();
        exchangeRateService.getExchangeRate(countryInfo.getCurrencies());
        return countryInfo;
    }
}
