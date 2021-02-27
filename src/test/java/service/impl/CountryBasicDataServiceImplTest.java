package service.impl;

import com.google.gson.Gson;
import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.retrofit.Endpoints;
import com.meli.ipexercise.services.CountryBasicDataService;
import com.meli.ipexercise.services.ExchangeRateService;
import com.meli.ipexercise.services.impl.CountryBasicDataServiceImpl;
import okhttp3.Request;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class CountryBasicDataServiceImplTest {

    @Mock Endpoints endpoints;
    @Mock
    ExchangeRateService exchangeRateService;
    @InjectMocks
    CountryBasicDataService countryBasicDataService = new CountryBasicDataServiceImpl("https://api.ip2country.info/", exchangeRateService);

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(countryBasicDataService, "endpoints", endpoints);
        ReflectionTestUtils.setField(countryBasicDataService, "exchangeRateService", exchangeRateService);

    }

    @Test
    public void getCountryBasicDataTestNull() throws IOException {
        CountryInfo info = countryBasicDataService.getCountryBasicData(new IpInfoDto());
        Assert.assertNotNull(info);
        Assert.assertNull(info.getAlpha3Code());
    }

    @Test
    public void getCountryBasicDataTestEmpty() throws IOException {
        Mockito.when(endpoints.getCountryInfo(Mockito.anyString())).thenReturn(getCountryInfo());
        Mockito.when(exchangeRateService.getExchangeRate(Mockito.any())).thenReturn(new ExchangeRate());
        CountryInfo info = countryBasicDataService.getCountryBasicData(new IpInfoDto());
        Assert.assertNotNull(info);
        Assert.assertNull(info.getAlpha3Code());
    }

    private Call<CountryInfo> getCountryInfo(){
        return new Call<CountryInfo>() {
            @Override
            public Response<CountryInfo> execute() throws IOException {
                return getCountryInfo2();
            }

            @Override
            public void enqueue(Callback<CountryInfo> callback) {

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
            public Call<CountryInfo> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

    }

    private Response<CountryInfo> getCountryInfo2(){
        CountryInfo countryInfo = new Gson()
                .fromJson(
                        "{\"name\":\"Colombia\",\"topLevelDomain\":[\".co\"],\"alpha2Code\":\"CO\",\"alpha3Code\":\"COL\",\"callingCodes\":[\"57\"],\"capital\":\"Bogotá\",\"altSpellings\":[\"CO\",\"Republic of Colombia\",\"República de Colombia\"],\"region\":\"Americas\",\"subregion\":\"South America\",\"population\":48759958,\"latlng\":[4.0,-72.0],\"demonym\":\"Colombian\",\"area\":1141748.0,\"gini\":55.9,\"timezones\":[\"UTC-05:00\"],\"borders\":[\"BRA\",\"ECU\",\"PAN\",\"PER\",\"VEN\"],\"nativeName\":\"Colombia\",\"numericCode\":\"170\",\"currencies\":[{\"code\":\"COP\",\"name\":\"Colombian peso\",\"symbol\":\"$\"}],\"languages\":[{\"iso639_1\":\"es\",\"iso639_2\":\"spa\",\"name\":\"Spanish\",\"nativeName\":\"Español\"}],\"translations\":{\"de\":\"Kolumbien\",\"es\":\"Colombia\",\"fr\":\"Colombie\",\"ja\":\"コロンビア\",\"it\":\"Colombia\",\"br\":\"Colômbia\",\"pt\":\"Colômbia\",\"nl\":\"Colombia\",\"hr\":\"Kolumbija\",\"fa\":\"کلمبیا\"},\"flag\":\"https://restcountries.eu/data/col.svg\",\"regionalBlocs\":[{\"acronym\":\"PA\",\"name\":\"Pacific Alliance\",\"otherAcronyms\":[],\"otherNames\":[\"Alianza del Pacífico\"]},{\"acronym\":\"USAN\",\"name\":\"Union of South American Nations\",\"otherAcronyms\":[\"UNASUR\",\"UNASUL\",\"UZAN\"],\"otherNames\":[\"Unión de Naciones Suramericanas\",\"União de Nações Sul-Americanas\",\"Unie van Zuid-Amerikaanse Naties\",\"South American Union\"]}],\"cioc\":\"COL\"}",
                        CountryInfo.class);
        return Response.success(countryInfo);

    }
}
