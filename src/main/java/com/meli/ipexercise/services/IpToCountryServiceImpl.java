package com.meli.ipexercise.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meli.ipexercise.Mapper.IpInfoMapper;
import com.meli.ipexercise.models.IpInfo;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.retrofit.UnsafeOkHttpClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@ConfigurationProperties("http-services")
@Service("IpToCountryService")
public class IpToCountryServiceImpl implements IpToCountryService{

    private Endpoints endpoints;
    private IpInfo ipInfo;
    private CountryBasicDataService countryBasicDataService;

    public IpToCountryServiceImpl(@Value("${http-services.endpoints.IP_INFO.host}") String url, CountryBasicDataService countryBasicDataService){
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
        this.countryBasicDataService = countryBasicDataService;
    }

    @Override
    public IpInfoDto getCountryFromIp(IpRequest ipAddress) throws IOException {
        ipInfo = endpoints.getIpInfo("ip?"+ipAddress.getIp()).execute().body();
        IpInfoDto ipInfoDto = IpInfoMapper.INSTANCE.ipInfo2IpInfoDto(ipInfo);
        ipInfoDto.setIp(ipAddress.getIp());
        countryBasicDataService.getCountryBasicData(ipInfoDto);
        return ipInfoDto;
    }

}
