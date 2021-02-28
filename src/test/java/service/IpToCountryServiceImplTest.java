package service;

import com.google.gson.Gson;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.models.IpInfo;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.services.CountryBasicDataService;
import com.meli.ipexercise.services.ExchangeRateService;
import com.meli.ipexercise.services.IpToCountryService;
import com.meli.ipexercise.services.impl.ExchangeRateServiceImpl;
import com.meli.ipexercise.services.impl.IpToCountryServiceImpl;
import okhttp3.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class IpToCountryServiceImplTest {

    @Mock
    Endpoints endpoints;
    @Mock
    CountryBasicDataService countryBasicDataService;
    @InjectMocks
    IpToCountryServiceImpl ipToCountryService = new IpToCountryServiceImpl("https://api.ip2country.info/", countryBasicDataService);

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(ipToCountryService, "endpoints", endpoints);
    }

    @Test
    public void getCountryFromIpTest() throws IOException {
        Mockito.when(endpoints.getIpInfo(Mockito.anyString())).thenReturn(getIpInfoCall());
        IpInfoDto rate = ipToCountryService.getCountryFromIp(new IpRequest("127.0.0.1"));
        Assert.assertNotNull(rate);
    }

    private Call<IpInfo> getIpInfoCall(){

        return new Call<IpInfo>() {
            @Override
            public Response<IpInfo> execute() throws IOException {
                return getIpInfoResponse();
            }

            @Override
            public void enqueue(Callback<IpInfo> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<IpInfo> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    private Response<IpInfo> getIpInfoResponse(){
        IpInfo ipInfo = new Gson()
                .fromJson(
                        "{\"countryCode\":\"CO\",\"countryCode3\":\"COL\",\"countryName\":\"Colombia\",\"countryEmoji\":\"\uD83C\uDDE8\uD83C\uDDF3\"}",
                        IpInfo.class);
        return Response.success(ipInfo);
    }

}
