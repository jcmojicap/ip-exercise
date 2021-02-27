package com.meli.ipexercise.controller;

import com.meli.ipexercise.models.DataResponse;
import com.meli.ipexercise.models.IpRequest;
import com.meli.ipexercise.models.UseStatistics;
import com.meli.ipexercise.services.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class IpExerciseController {

    private final DataService dataService;

    IpExerciseController(DataService dataService){
        this.dataService = dataService;
    }

    @PostMapping(path = "/trace")
    public DataResponse obtainTrace(@RequestBody IpRequest ipRequest) throws IOException {
        return dataService.getCountryInfoFromIp(ipRequest);
    }

    @GetMapping(path = "/stats")
    public UseStatistics getStats() throws IOException {
        return dataService.getUsageStatistics();
    }
}
