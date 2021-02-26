package com.meli.ipexercise.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryStatisticsRepository extends JpaRepository<CountryStatistics, Long> {

    CountryStatistics findByPais(String pais);

    @Query("SELECT c FROM CountryStatistics c WHERE c.distancia = (SELECT MAX(c.distancia) FROM CountryStatistics c)")
    List<CountryStatistics> getFartherCountry();

    @Query("SELECT c FROM CountryStatistics c WHERE c.distancia = (SELECT MIN(c.distancia) FROM CountryStatistics c)")
    List<CountryStatistics> getNearestCountry();
}
