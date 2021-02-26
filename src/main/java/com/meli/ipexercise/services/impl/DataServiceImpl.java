package com.meli.ipexercise.services.impl;

import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.CountryStatisticsDto;
import com.meli.ipexercise.models.DataResponse;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.models.UseStatistics;
import com.meli.ipexercise.persistence.CountryStatistics;
import com.meli.ipexercise.services.CountryBasicDataService;
import com.meli.ipexercise.services.CountryStatisticsService;
import com.meli.ipexercise.services.DataService;
import com.meli.ipexercise.services.ExchangeRateService;
import com.meli.ipexercise.services.IpToCountryService;
import com.meli.ipexercise.services.helper.Constants;
import com.meli.ipexercise.services.helper.DataResponseBuilder;
import com.meli.ipexercise.services.helper.Utils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    private IpToCountryService ipToCountryService;
    private CountryBasicDataService countryBasicDataService;
    private ExchangeRateService exchangeRateService;
    private DataResponseBuilder dataResponseBuilder;
    private CountryStatisticsService countryStatisticsService;

    public DataServiceImpl(IpToCountryService ipToCountryService,
                           CountryBasicDataService countryBasicDataService,
                           ExchangeRateService exchangeRateService,
                           DataResponseBuilder dataResponseBuilder,
                           CountryStatisticsService countryStatisticsService){
        this.ipToCountryService = ipToCountryService;
        this.countryBasicDataService = countryBasicDataService;
        this.exchangeRateService = exchangeRateService;
        this.dataResponseBuilder = dataResponseBuilder;
        this.countryStatisticsService = countryStatisticsService;
    }

    @Override
    public DataResponse getCountryInfoFromIp(IpRequest ipRequest) throws IOException {
        IpInfoDto ipInfoDto = ipToCountryService.getCountryFromIp(ipRequest);
        DataResponse response = new DataResponse();
        if (ipInfoDto.getCountryCode3().isEmpty()){
            response.setError(Constants.IP_NOT_FOUND);
            return response;
        }
        CountryInfo countryInfo = countryBasicDataService.getCountryBasicData(ipInfoDto);
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(countryInfo.getCurrencies());
        response = dataResponseBuilder.buildDataResponse(ipInfoDto, countryInfo, exchangeRate);
        saveStatistics(response);
        return response;
    }

    @Async
    void saveStatistics(DataResponse response){
        CountryStatistics countryStatistics = countryStatisticsService.getCountryByName(response.getCountry());
        if (countryStatistics == null){
            countryStatistics = new CountryStatistics();
            countryStatistics.setPais(response.getCountry());
            countryStatistics.setDistancia(Long.valueOf(response.getEstimatedDistance()));
            countryStatistics.setCantidadInvocaciones(1);
        } else {
            int times = countryStatistics.getCantidadInvocaciones();
            countryStatistics.setCantidadInvocaciones(++times);
        }
        countryStatisticsService.persistCountry(countryStatistics);

    }

    @Override
    public UseStatistics getUsageStatistics() {

        CountryStatisticsDto fartherCountry = buildCountryStatistics(countryStatisticsService.getFartherCountryStatistics());
        CountryStatisticsDto nearestCountry = buildCountryStatistics(countryStatisticsService.getNearestCountryStatistics());
        UseStatistics useStatistics = new UseStatistics();
        useStatistics.setFartherCountry(fartherCountry.getDistance() != null ? fartherCountry : null);
        useStatistics.setNearestCountry(nearestCountry.getDistance() !=null ? nearestCountry : null);
        try {
            useStatistics.setAverage(calculateAverage(fartherCountry, nearestCountry));
        } catch (ArithmeticException e){
            useStatistics.setError(Constants.STATISTICS_NOT_FOUND);
        } catch (Exception e){
            useStatistics.setError(Constants.SERVICE_UNAVAILABLE);
        }
        return useStatistics;
    }

    private CountryStatisticsDto buildCountryStatistics(List<CountryStatistics> countryStatistics){
        CountryStatisticsDto dto = new CountryStatisticsDto();
        String countriesNames = "";
        int invocations = 0;
        for (CountryStatistics country: countryStatistics) {
            countriesNames += country.getPais() + ", ";
            invocations += country.getCantidadInvocaciones();
        }
        dto.setCountry(countriesNames);
        dto.setInvocationsQuantity(invocations);
        dto.setDistance(countryStatistics.stream().findFirst().orElse(new CountryStatistics()).getDistancia());
        dto.setDistanceMeasure(Constants.KILOMETERS);
        return dto;
    }

    private String calculateAverage(CountryStatisticsDto country1, CountryStatisticsDto country2){
        int base = country1.getInvocationsQuantity() + country2.getInvocationsQuantity();
        if (base == 0){
            throw new ArithmeticException();
        }
        return Utils.formatDistance((((country1.getDistance() * country1.getInvocationsQuantity()) + (country2.getDistance() * country2.getInvocationsQuantity())) / base)) + " KM";
    }
}
