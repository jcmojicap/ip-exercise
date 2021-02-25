package com.meli.ipexercise.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CountryInfo {

    private String name;
    private List<String> topLevelDomain;
    private String alpha2Code;
    private String alpha3Code;
    private List<String> callingCodes;
    private String capital;
    private List<String> altSpellings;
    private String region;
    private String subregion;
    private long population;
    private List<Double> latlng;
    private String demonym;
    private double area;
    private double gini;
    private List<String> timezones;
    private List<String> borders;
    private String nativeName;
    private String numericCode;
    private List<CountryCurrency> currencies;
    private List<CountryLanguage> languages;
    private CountryNameTranslation translations;
    private String flag;
    private List<RegionalBloc> regionalBlocs;
    private String cioc;

}
