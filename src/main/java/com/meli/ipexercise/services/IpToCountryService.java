package com.meli.ipexercise.services;

import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.models.IpRequest;

import java.io.IOException;

public interface IpToCountryService {

    IpInfoDto getCountryFromIp(IpRequest ipAddress) throws IOException;
}
