package com.meli.ipexercise.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.IpInfo;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.retrofit.UnsafeOkHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class CountryBasicDataServiceImpl implements CountryBasicDataService{

    private Endpoints endpoints;
    private CountryInfo countryInfo;

    private ExchangeRateService exchangeRateService;

    public CountryBasicDataServiceImpl(@Value("${http-services.endpoints.COUNTRY_INFO.host}") String url, ExchangeRateService exchangeRateService){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .build();
        endpoints = retrofit.create(Endpoints.class);
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public CountryInfo getCountryBasicData(IpInfoDto ipInfoDto) throws IOException {
        CountryInfo countryInfo = endpoints.getCountryInfo(ipInfoDto.getCountryCode3()).execute().body();
        exchangeRateService.getExchangeRate(countryInfo.getCurrencies());
        return countryInfo;
    }
}
