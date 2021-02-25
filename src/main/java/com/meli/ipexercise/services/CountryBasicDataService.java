package com.meli.ipexercise.services;

import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.IpInfoDto;

import java.io.IOException;

public interface CountryBasicDataService {

    CountryInfo getCountryBasicData(IpInfoDto ipInfoDto) throws IOException;
}
