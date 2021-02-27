package com.meli.ipexercise.controller;

import com.meli.ipexercise.models.CountryStatisticsDto;
import com.meli.ipexercise.models.DataResponse;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.models.UseStatistics;
import com.meli.ipexercise.services.DataService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IpExerciseControllerTest {

    @Mock
     DataService dataService;

    @InjectMocks
    IpExerciseController ipExerciseController;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(
                ipExerciseController, "dataService", dataService);
    }

    @Test
    public void obtainTraceTest() throws IOException {
        Mockito.when(this.dataService.getCountryInfoFromIp(Mockito.any())).thenReturn(this.getDataResponse(false));

        DataResponse response = ipExerciseController.obtainTrace(this.getIpRequest());
        assertNotNull(response);
        assertEquals("Colombia", response.getCountry());
    }

    @Test
    public void obtainTraceTestError() throws IOException {
        Mockito.when(this.dataService.getCountryInfoFromIp(Mockito.any())).thenReturn(this.getDataResponse(true));

        DataResponse response = ipExerciseController.obtainTrace(this.getIpRequest());
        assertNotNull(response);
        assertEquals("Error", response.getError());
    }

    @Test
    public void getStatsTest() throws IOException {
        Mockito.when(this.dataService.getUsageStatistics()).thenReturn(this.getUseStatistics(false));

        UseStatistics response = ipExerciseController.getStats();
        assertNotNull(response);
        assertEquals("Colombia", response.getFartherCountry().getCountry());
        assertEquals("12345 KM", response.getFartherCountry().getDistance() + " " + response.getFartherCountry().getDistanceMeasure());
        assertEquals("Colombia", response.getFartherCountry().getCountry());
    }

    @Test
    public void getStatsTestError() throws IOException {
        Mockito.when(this.dataService.getUsageStatistics()).thenReturn(this.getUseStatistics(true));

        UseStatistics response = ipExerciseController.getStats();
        assertNotNull(response);
        assertEquals("Error", response.getError());
    }

    private UseStatistics getUseStatistics(boolean isError){
        UseStatistics useStatistics = new UseStatistics();
        if (isError){
            useStatistics.setError("Error");
            return useStatistics;
        }
        useStatistics.setAverage("12345 KM");
        useStatistics.setNearestCountry(getCountry());
        useStatistics.setFartherCountry(getCountry());
        return useStatistics;
    }

    private CountryStatisticsDto getCountry(){
        CountryStatisticsDto dto = new CountryStatisticsDto();
        dto.setDistanceMeasure("KM");
        dto.setDistance(12345L);
        dto.setCountry("Colombia");
        dto.setInvocationsQuantity(10);
        return dto;
    }

    private IpRequest getIpRequest(){
        IpRequest ipRequest = new IpRequest();
        ipRequest.setIp("127.0.0.1");
        return ipRequest;
    }

    private DataResponse getDataResponse(boolean isError){
        DataResponse response = new DataResponse();
        if (isError){
            response.setError("Error");
            return response;
        }
        response.setDistanceMeasure("KM");
        response.setEstimatedDistance("1234");
        response.setCurrency("USD");
        response.setIsoCode("COL");
        response.setCountry("Colombia");
        response.setIp("127.0.0.1");
        response.setDate(LocalDateTime.now());
        response.setOfficialLanguages(new ArrayList<>());

        return response;
    }
}
