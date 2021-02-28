package service;

import com.meli.ipexercise.persistence.CountryStatistics;
import com.meli.ipexercise.persistence.CountryStatisticsRepository;
import com.meli.ipexercise.services.impl.CountryStatisticsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

public class CountryStatisticsServiceImplTest {

    @Mock
    CountryStatisticsRepository countryStatisticsRepository;
    @InjectMocks CountryStatisticsServiceImpl countryStatisticsService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(countryStatisticsService, "countryStatisticsRepository", countryStatisticsRepository);
    }

    @Test
    public void getFartherCountryStatistics() {
        Mockito.when(this.countryStatisticsRepository.getFartherCountry()).thenReturn(getCountries());
        List<CountryStatistics> list = countryStatisticsRepository.getFartherCountry();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void getNearestCountryStatistics() {
        Mockito.when(this.countryStatisticsRepository.getNearestCountry()).thenReturn(getCountries());
        List<CountryStatistics> list = countryStatisticsRepository.getNearestCountry();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void getCountryByName() {
        Mockito.when(this.countryStatisticsRepository.findByPais(Mockito.anyString())).thenReturn(getCountry());
        CountryStatistics country = countryStatisticsRepository.findByPais("Colombia");
        Assert.assertNotNull(country);
        Assert.assertEquals("Colombia", country.getPais());

    }

    @Test
    public void persistCountry() {
        Mockito.when(this.countryStatisticsRepository.save(Mockito.any())).thenReturn(getCountry());
        CountryStatistics country = countryStatisticsRepository.save(getCountry());
        Assert.assertNotNull(country);
        Assert.assertNotNull(country.getId());

    }

    private List<CountryStatistics> getCountries(){
        List<CountryStatistics> countryStatistics = new ArrayList<>();
        countryStatistics.add(getCountry());
        return countryStatistics;
    }

    private CountryStatistics getCountry(){
        CountryStatistics country = new CountryStatistics();
        country.setId(1L);
        country.setDistancia(12345L);
        country.setPais("Colombia");
        country.setCantidadInvocaciones(4);
        return country;
    }
}
