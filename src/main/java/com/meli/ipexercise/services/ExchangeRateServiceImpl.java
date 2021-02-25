package com.meli.ipexercise.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meli.ipexercise.models.CountryCurrency;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.retrofit.UnsafeOkHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{

    private Endpoints endpoints;
    private ExchangeRate exchangeRate;
    @Value("${http-services.endpoints.EXCHANGE_INFO.access_key}")
    private String accessKey;

    public ExchangeRateServiceImpl(@Value("${http-services.endpoints.EXCHANGE_INFO.host}") String url){
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
    }

    @Override
    public ExchangeRate getExchangeRate(List<CountryCurrency> currencies) throws IOException {
        exchangeRate = endpoints.getExchangeRates(accessKey).execute().body();
        return exchangeRate;
    }
}
