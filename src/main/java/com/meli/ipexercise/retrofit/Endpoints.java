package com.meli.ipexercise.retrofit;

import com.google.gson.JsonObject;
import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.models.IpInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface Endpoints {

    @GET()
    Call<IpInfo> getIpInfo(@Url String ipAddress);

    @GET("/rest/v2/alpha/{countryCode}")
    Call<CountryInfo> getCountryInfo(@Path("countryCode") String countryCode);

    @GET("/api/latest")
    Call<ExchangeRate> getExchangeRates(@Query("access_key") String accessKey);


}
