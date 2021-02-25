package com.meli.ipexercise.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryLanguage {

    @SerializedName("iso639_1")
    private String iso6391;
    @SerializedName("iso639_2")
    private String iso6392;
    private String name;
    private String nativeName;

}
