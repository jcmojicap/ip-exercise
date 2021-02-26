package com.meli.ipexercise.services.impl;

import com.meli.ipexercise.persistence.CountryStatistics;
import com.meli.ipexercise.persistence.CountryStatisticsRepository;
import com.meli.ipexercise.services.CountryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryStatisticsServiceImpl implements CountryStatisticsService {

    @Autowired
    public CountryStatisticsRepository countryStatisticsRepository;

    @Override
    public List<CountryStatistics> getFartherCountryStatistics() {
        return countryStatisticsRepository.getFartherCountry();
    }

    @Override
    public List<CountryStatistics> getNearestCountryStatistics() {
        return countryStatisticsRepository.getNearestCountry();
    }

    @Override
    public CountryStatistics getCountryByName(String countryName) {
        return countryStatisticsRepository.findByPais(countryName);
    }

    @Override
    public void persistCountry(CountryStatistics countryStatistics) {
        countryStatisticsRepository.save(countryStatistics);
    }
}
