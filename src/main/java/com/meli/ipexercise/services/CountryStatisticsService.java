package com.meli.ipexercise.services;

import com.meli.ipexercise.persistence.CountryStatistics;

import java.util.List;

public interface CountryStatisticsService {

    List<CountryStatistics> getFartherCountryStatistics();

    List<CountryStatistics> getNearestCountryStatistics();

    CountryStatistics getCountryByName(String countryName);

    void persistCountry(CountryStatistics countryStatistics);
}
