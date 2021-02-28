package service;

import com.google.gson.Gson;
import com.meli.ipexercise.models.Coordenate;
import com.meli.ipexercise.models.CountryInfo;
import com.meli.ipexercise.models.DataResponse;
import com.meli.ipexercise.models.ExchangeRate;
import com.meli.ipexercise.models.IpInfoDto;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.models.UseStatistics;
import com.meli.ipexercise.persistence.CountryStatistics;
import com.meli.ipexercise.services.CountryBasicDataService;
import com.meli.ipexercise.services.CountryStatisticsService;
import com.meli.ipexercise.services.ExchangeRateService;
import com.meli.ipexercise.services.IpToCountryService;
import com.meli.ipexercise.services.helper.Constants;
import com.meli.ipexercise.services.helper.DataResponseBuilder;
import com.meli.ipexercise.services.helper.DistanceBuilder;
import com.meli.ipexercise.services.helper.Utils;
import com.meli.ipexercise.services.impl.DataServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Instant.now;

public class DataServiceImplTest {

    @Mock IpToCountryService ipToCountryService;
    @Mock CountryBasicDataService countryBasicDataService;
    @Mock ExchangeRateService exchangeRateService;
    @Mock DataResponseBuilder dataResponseBuilder;
    @Mock CountryStatisticsService countryStatisticsService;
    @InjectMocks DataServiceImpl dataServiceImpl;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(dataServiceImpl, "ipToCountryService", ipToCountryService);
        ReflectionTestUtils.setField(dataServiceImpl, "countryBasicDataService", countryBasicDataService);
        ReflectionTestUtils.setField(dataServiceImpl, "exchangeRateService", exchangeRateService);
        ReflectionTestUtils.setField(dataServiceImpl, "dataResponseBuilder", dataResponseBuilder);
        ReflectionTestUtils.setField(dataServiceImpl, "countryStatisticsService", countryStatisticsService);

    }

    @Test
    public void getCountryInfoFromIpTest() throws IOException {
        Mockito.when(this.ipToCountryService.getCountryFromIp(Mockito.any())).thenReturn(getIpInfoDto(false));
        Mockito.when(this.countryBasicDataService.getCountryBasicData(Mockito.any())).thenReturn(getCountryInfo());
        Mockito.when(this.exchangeRateService.getExchangeRate(Mockito.any())).thenReturn(getExchangeRate(false));
        Mockito.when(this.dataResponseBuilder.buildDataResponse(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(getDataResponse());

        DataResponse response = dataServiceImpl.getCountryInfoFromIp(new IpRequest("127.0.0.1"));
        Assert.assertNotNull(response);
        Assert.assertEquals("Colombia", response.getCountry());
    }

    @Test
    public void getCountryInfoFromIpTestEmpty() throws IOException {
        Mockito.when(this.ipToCountryService.getCountryFromIp(Mockito.any())).thenReturn(getIpInfoDto(true));
        Mockito.when(this.countryBasicDataService.getCountryBasicData(Mockito.any())).thenReturn(getCountryInfo());
        Mockito.when(this.exchangeRateService.getExchangeRate(Mockito.any())).thenReturn(getExchangeRate(false));
        Mockito.when(this.dataResponseBuilder.buildDataResponse(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(getDataResponse());
        Mockito.when(this.countryStatisticsService.getCountryByName(Mockito.anyString())).thenReturn(getCountryStatistics(false));
        Mockito.doNothing().when(this.countryStatisticsService).persistCountry(Mockito.any());
        DataResponse response = dataServiceImpl.getCountryInfoFromIp(new IpRequest("127.0.0.1"));
        Assert.assertNotNull(response);
        Assert.assertEquals("La dirección IP no fue encontrada, verifique que esté bien formada o que es de acceso público", response.getError());
    }

    @Test
    public void getCountryInfoFromIpTestEmpty2() throws IOException {
        Mockito.when(this.ipToCountryService.getCountryFromIp(Mockito.any())).thenReturn(getIpInfoDto(true));
        Mockito.when(this.countryBasicDataService.getCountryBasicData(Mockito.any())).thenReturn(getCountryInfo());
        Mockito.when(this.exchangeRateService.getExchangeRate(Mockito.any())).thenReturn(getExchangeRate(false));
        Mockito.when(this.dataResponseBuilder.buildDataResponse(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(getDataResponse());
        Mockito.when(this.countryStatisticsService.getCountryByName(Mockito.anyString())).thenReturn(new CountryStatistics());
        Mockito.doNothing().when(this.countryStatisticsService).persistCountry(Mockito.any());
        DataResponse response = dataServiceImpl.getCountryInfoFromIp(new IpRequest("127.0.0.1"));
        Assert.assertNotNull(response);
        Assert.assertEquals("La dirección IP no fue encontrada, verifique que esté bien formada o que es de acceso público", response.getError());
    }

    @Test
    public void getUsageStatisticsTest(){
        Mockito.when(this.countryStatisticsService.getFartherCountryStatistics()).thenReturn(Arrays.asList(getCountryStatistics(false)));
        Mockito.when(this.countryStatisticsService.getNearestCountryStatistics()).thenReturn(Arrays.asList(getCountryStatistics(false)));
        UseStatistics useStatistics = dataServiceImpl.getUsageStatistics();
        Assert.assertNotNull(useStatistics);
    }

    @Test
    public void getUsageStatisticsTestError(){
        Mockito.when(this.countryStatisticsService.getFartherCountryStatistics()).thenReturn(Arrays.asList(getCountryStatistics(true)));
        Mockito.when(this.countryStatisticsService.getNearestCountryStatistics()).thenReturn(Arrays.asList(getCountryStatistics(true)));
        UseStatistics useStatistics = dataServiceImpl.getUsageStatistics();
        Assert.assertNotNull(useStatistics);
        Assert.assertNotNull(useStatistics.getError());
    }

    private CountryStatistics getCountryStatistics(boolean hasZeroInvocations){
        CountryStatistics statistics = new CountryStatistics();
        if (hasZeroInvocations){
            statistics.setCantidadInvocaciones(0);
        } else {
            statistics.setCantidadInvocaciones(1);
        }
        statistics.setPais("Colombia");
        statistics.setDistancia(12345L);
        statistics.setId(1L);
        return statistics;
    }

    private IpInfoDto getIpInfoDto(boolean isEmpty){
        IpInfoDto dto = new IpInfoDto();
        if (isEmpty){
            dto.setIp("");
            dto.setCountryCode("");
            dto.setCountryCode3("");
            dto.setCountryName("");
            return dto;
        }
        dto.setIp("127.0.0.1");
        dto.setCountryCode("COL");
        dto.setCountryCode3("COL");
        dto.setCountryName("COL");
        return dto;
    }

    private CountryInfo getCountryInfo(){
        return new Gson()
                .fromJson(
                        "{\"name\":\"Colombia\",\"topLevelDomain\":[\".co\"],\"alpha2Code\":\"CO\",\"alpha3Code\":\"COL\",\"callingCodes\":[\"57\"],\"capital\":\"Bogotá\",\"altSpellings\":[\"CO\",\"Republic of Colombia\",\"República de Colombia\"],\"region\":\"Americas\",\"subregion\":\"South America\",\"population\":48759958,\"latlng\":[4.0,-72.0],\"demonym\":\"Colombian\",\"area\":1141748.0,\"gini\":55.9,\"timezones\":[\"UTC-05:00\"],\"borders\":[\"BRA\",\"ECU\",\"PAN\",\"PER\",\"VEN\"],\"nativeName\":\"Colombia\",\"numericCode\":\"170\",\"currencies\":[{\"code\":\"COP\",\"name\":\"Colombian peso\",\"symbol\":\"$\"}],\"languages\":[{\"iso639_1\":\"es\",\"iso639_2\":\"spa\",\"name\":\"Spanish\",\"nativeName\":\"Español\"}],\"translations\":{\"de\":\"Kolumbien\",\"es\":\"Colombia\",\"fr\":\"Colombie\",\"ja\":\"コロンビア\",\"it\":\"Colombia\",\"br\":\"Colômbia\",\"pt\":\"Colômbia\",\"nl\":\"Colombia\",\"hr\":\"Kolumbija\",\"fa\":\"کلمبیا\"},\"flag\":\"https://restcountries.eu/data/col.svg\",\"regionalBlocs\":[{\"acronym\":\"PA\",\"name\":\"Pacific Alliance\",\"otherAcronyms\":[],\"otherNames\":[\"Alianza del Pacífico\"]},{\"acronym\":\"USAN\",\"name\":\"Union of South American Nations\",\"otherAcronyms\":[\"UNASUR\",\"UNASUL\",\"UZAN\"],\"otherNames\":[\"Unión de Naciones Suramericanas\",\"União de Nações Sul-Americanas\",\"Unie van Zuid-Amerikaanse Naties\",\"South American Union\"]}],\"cioc\":\"COL\"}",
                        CountryInfo.class);

    }

    private ExchangeRate getExchangeRate(boolean isError){
        if (isError){
            return new ExchangeRate();
        }
        ExchangeRate rate = new Gson()
                .fromJson(
                        "{\"success\":true,\"timestamp\":1613865066,\"base\":\"EUR\",\"date\":\"2021-02-21\",\"rates\":{\"AED\":4.45116,\"AFN\":93.99,\"ALL\":123.72,\"AMD\":635.578463,\"ANG\":2.1788,\"AOA\":790.667826,\"ARS\":108.0972,\"AUD\":1.539803,\"AWG\":2.181285,\"AZN\":2.064913,\"BAM\":1.95583,\"BBD\":2.4508,\"BDT\":102.7685,\"BGN\":1.9558,\"BHD\":0.457,\"BIF\":2359.519995,\"BMD\":1.211825,\"BND\":1.6071,\"BOB\":8.3936,\"BRL\":6.5231,\"BSD\":1.2138,\"BTC\":0.000021646372,\"BTN\":88.0211,\"BWP\":13.151,\"BYN\":3.1438,\"BYR\":23751.769953,\"BZD\":2.4467,\"CAD\":1.528778,\"CDF\":2396.388683,\"CHF\":1.086092,\"CLF\":0.031122,\"CLP\":858.760264,\"CNY\":7.860871,\"COP\":4305.799991,\"CRC\":742.739999,\"CUC\":1.211825,\"CUP\":32.113362,\"CVE\":110.265,\"CZK\":25.89155,\"DJF\":216.09,\"DKK\":7.43649,\"DOP\":70.293,\"DZD\":160.694103,\"EGP\":18.954,\"ERN\":18.177809,\"ETB\":48.8765,\"EUR\":1,\"FJD\":2.455097,\"FKP\":0.864938,\"GBP\":0.86457,\"GEL\":4.011614,\"GGP\":0.864938,\"GHS\":7.0098,\"GIP\":0.864938,\"GMD\":62.348868,\"GNF\":12314.299975,\"GTQ\":9.3707,\"GYD\":253.949999,\"HKD\":9.395527,\"HNL\":29.251,\"HRK\":7.570882,\"HTG\":88.522,\"HUF\":358.451823,\"IDR\":17046.511794,\"ILS\":3.963928,\"IMP\":0.864938,\"INR\":87.942751,\"IQD\":1770.969996,\"IRR\":51023.89195,\"ISK\":155.602954,\"JEP\":0.864938,\"JMD\":182.978,\"JOD\":0.858341,\"JPY\":127.873596,\"KES\":133.1593,\"KGS\":102.562857,\"KHR\":4937.79999,\"KMF\":492.49028,\"KPW\":1090.641621,\"KRW\":1339.067046,\"KWD\":0.366703,\"KYD\":1.0116,\"KZT\":506.699999,\"LAK\":11340.799977,\"LBP\":1835.499996,\"LKR\":237.3028,\"LRD\":209.646147,\"LSL\":17.802172,\"LTL\":3.578205,\"LVL\":0.733021,\"LYD\":5.4041,\"MAD\":10.8062,\"MDL\":21.3451,\"MGA\":4575.569991,\"MKD\":61.615,\"MMK\":1711.493497,\"MNT\":3460.137548,\"MOP\":9.6935,\"MRO\":432.621316,\"MUR\":48.17,\"MVR\":18.666638,\"MWK\":945.162998,\"MXN\":24.75421,\"MYR\":4.894608,\"MZN\":90.996397,\"NAD\":17.802166,\"NGN\":461.705741,\"NIO\":42.362,\"NOK\":10.256649,\"NPR\":140.834,\"NZD\":1.658558,\"OMR\":0.46658,\"PAB\":1.2139,\"PEN\":4.4329,\"PGK\":4.3166,\"PHP\":58.765,\"PKR\":193.1196,\"PLN\":4.48509,\"PYG\":8071.099984,\"QAR\":4.412301,\"RON\":4.87643,\"RSD\":117.56,\"RUB\":89.71751,\"RWF\":1205.469998,\"SAR\":4.544348,\"SBD\":9.702984,\"SCR\":26.056,\"SDG\":66.983673,\"SEK\":10.031856,\"SGD\":1.6064,\"SHP\":0.864938,\"SLL\":12378.792761,\"SOS\":710.129859,\"SRD\":17.152217,\"STD\":24578.396965,\"SVC\":10.622,\"SYP\":621.394433,\"SZL\":17.808,\"THB\":36.336619,\"TJS\":13.8315,\"TMT\":4.241387,\"TND\":3.280456,\"TOP\":2.790353,\"TRY\":8.43879,\"TTD\":8.2351,\"TWD\":33.844944,\"TZS\":2814.859994,\"UAH\":33.8042,\"UGX\":4452.299991,\"USD\":1.211825,\"UYU\":51.976,\"UZS\":12784.009975,\"VEF\":12.103107,\"VND\":27941.999944,\"VUV\":130.026527,\"WST\":3.060192,\"XAF\":655.956999,\"XAG\":0.044427,\"XAU\":0.000679,\"XCD\":3.275018,\"XDR\":0.8432,\"XOF\":655.956999,\"XPF\":119.546977,\"YER\":303.410729,\"ZAR\":17.756878,\"ZMK\":10907.883522,\"ZMW\":26.2975,\"ZWL\":390.208051}}",
                        ExchangeRate.class);
        return rate;
    }

    private DataResponse getDataResponse(){
        DataResponse response = new DataResponse();
        response.setIp("127.0.0.1");
        response.setDate(LocalDateTime.now());
        response.setCountry("Colombia");
        response.setIsoCode("COL");
        response.setOfficialLanguages(Arrays.asList("Español"));
        response.setTimes(getTimes());
        response.setCurrency("COP (1 EUR = 4500 $)");
        response.setEstimatedDistance("12345");
        response.setDistanceMeasure(Constants.KILOMETERS);
        return response;
    }

    private List<ZonedDateTime> getTimes(){
        List<ZonedDateTime> list = new ArrayList<>();
        list.add(getTime());
        return list;
    }

    private ZonedDateTime getTime(){
        ZoneId zoneId = ZoneId.of("UTC-5");
        ZonedDateTime time = ZonedDateTime.ofInstant(now(), zoneId);
        return time;
    }

}
