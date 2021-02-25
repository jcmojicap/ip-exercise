package com.meli.ipexercise.services;

import com.meli.ipexercise.models.CountryCurrency;
import com.meli.ipexercise.models.ExchangeRate;

import java.io.IOException;
import java.util.List;

public interface ExchangeRateService {

    ExchangeRate getExchangeRate(List<CountryCurrency> currencies) throws IOException;
}
