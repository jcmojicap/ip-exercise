package com.meli.ipexercise.services.helper;

import com.meli.ipexercise.models.Coordenate;
import com.meli.ipexercise.models.CountryCurrency;
import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.DataResponse;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.models.IpInfoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.now;

@Service
public class DataResponseBuilder {

    public DataResponse buildDataResponse(IpInfoDto ipInfoDto, CountryInfo countryInfo, ExchangeRate exchangeRate){
        DataResponse response = new DataResponse();
        response.setIp(ipInfoDto.getIp());
        response.setDate(LocalDateTime.now());
        response.setCountry(countryInfo.getTranslations().getEspanol());
        response.setIsoCode(ipInfoDto.getCountryCode3());
        response.setOfficialLanguages(countryInfo.getLanguages().stream().map(f -> f.getName()).collect(Collectors.toList()));
        response.setTimes(getTimes(countryInfo.getTimezones()));
        response.setCurrency(getCurrencyExchange(countryInfo.getCurrencies(), exchangeRate));
        Coordenate bsAs = new Coordenate(DistanceBuilder.LATITUD_BS_AS, DistanceBuilder.LONGITUD_BS_AS);
        Coordenate country = new Coordenate(countryInfo.getLatlng().get(0), countryInfo.getLatlng().get(1));
        response.setEstimatedDistance(Utils.formatDistance(DistanceBuilder.distance(bsAs, country)));
        response.setDistanceMeasure(Constants.KILOMETERS);
        return response;
    }

    private List<ZonedDateTime> getTimes(List<String> timezones){
        List<ZonedDateTime> times = timezones.stream().map(f -> {
            ZoneId zoneId = ZoneId.of(f);
            ZonedDateTime time = ZonedDateTime.ofInstant(now(), zoneId);
            return time;
        }).collect(Collectors.toList());
        return times;
    }

    private String getCurrencyExchange(List<CountryCurrency> countryCurrencies, ExchangeRate exchangeRate){
        StringBuilder builder = new StringBuilder();
        countryCurrencies.forEach(f -> {
            String currency = f.getCode();
            String symbol = f.getSymbol();
            try{
                String exRate = exchangeRate.getRates().get(currency);
                double conversion = 1/Double.valueOf(exRate);
                builder.append(String.format("%s (1 EUR = %f %s)", currency, conversion, symbol));
            } catch (Exception e){
                builder.append(String.format("%s (Cotización en Euros no disponible en el momento)", currency));
            }

        });
        return builder.toString();
    }

}
