package com.meli.ipexercise.services;

import com.meli.ipexercise.models.DataResponse;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.models.UseStatistics;

import java.io.IOException;

public interface DataService {

    DataResponse getCountryInfoFromIp(IpRequest ipRequest) throws IOException;

    UseStatistics getUsageStatistics();

}
