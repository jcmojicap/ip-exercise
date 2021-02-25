package com.meli.ipexercise.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegionalBloc {

    private String acronym;
    private String name;
    private List<String> otherAcronyms;
    private List<String> otherNames;

}
