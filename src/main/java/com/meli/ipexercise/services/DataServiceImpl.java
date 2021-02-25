package com.meli.ipexercise.services;

import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.DataResponse;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.models.IpInfo;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.services.helper.DataResponseBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataServiceImpl implements DataService {

    private IpToCountryService ipToCountryService;
    private CountryBasicDataService countryBasicDataService;
    private ExchangeRateService exchangeRateService;
    private DataResponseBuilder dataResponseBuilder;

    public DataServiceImpl(IpToCountryService ipToCountryService,
                           CountryBasicDataService countryBasicDataService,
                           ExchangeRateService exchangeRateService,
                           DataResponseBuilder dataResponseBuilder){
        this.ipToCountryService = ipToCountryService;
        this.countryBasicDataService = countryBasicDataService;
        this.exchangeRateService = exchangeRateService;
        this.dataResponseBuilder = dataResponseBuilder;
    }

    @Override
    public DataResponse getCountryInfoFromIp(IpRequest ipRequest) throws IOException {
        IpInfoDto ipInfoDto = ipToCountryService.getCountryFromIp(ipRequest);
        CountryInfo countryInfo = countryBasicDataService.getCountryBasicData(ipInfoDto);
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(countryInfo.getCurrencies());

        return dataResponseBuilder.buildDataResponse(ipInfoDto, countryInfo, exchangeRate);
    }
}
