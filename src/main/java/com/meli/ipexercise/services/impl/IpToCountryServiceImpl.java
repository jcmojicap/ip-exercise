package com.meli.ipexercise.services.impl;

import com.meli.ipexercise.mapper.IpInfoMapper;
import com.meli.ipexercise.models.IpInfo;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.retrofit.RetrofitClient;
import com.meli.ipexercise.services.CountryBasicDataService;
import com.meli.ipexercise.services.IpToCountryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;

@ConfigurationProperties("http-services")
@Service("IpToCountryService")
public class IpToCountryServiceImpl implements IpToCountryService {

    private Endpoints endpoints;
    private IpInfo ipInfo;
    private CountryBasicDataService countryBasicDataService;
    private RetrofitClient retrofitClient = new RetrofitClient();

    public IpToCountryServiceImpl(@Value("${http-services.endpoints.IP_INFO.host}") String url, CountryBasicDataService countryBasicDataService){
        endpoints = retrofitClient.createRetrofitClient(url);
        this.countryBasicDataService = countryBasicDataService;
    }

    @Override
    public IpInfoDto getCountryFromIp(IpRequest ipRequest) throws IOException {
        ipInfo = endpoints.getIpInfo("ip?"+ipRequest.getIp()).execute().body();
        IpInfoDto ipInfoDto = IpInfoMapper.INSTANCE.ipInfo2IpInfoDto(ipInfo);
        ipInfoDto.setIp(ipRequest.getIp());
        return ipInfoDto;
    }

}
