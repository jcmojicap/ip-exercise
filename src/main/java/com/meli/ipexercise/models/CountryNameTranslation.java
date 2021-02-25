package com.meli.ipexercise.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryNameTranslation {
    @SerializedName("de")
    private String deutsch;
    @SerializedName("es")
    private String espanol;
    @SerializedName("fr")
    private String francais;
    @SerializedName("ja")
    private String japanese;
    @SerializedName("it")
    private String italian;
    @SerializedName("br")
    private String breton;
    @SerializedName("pt")
    private String portuguese;
    @SerializedName("nl")
    private String nederlands;
    @SerializedName("hr")
    private String hrvatski;
    @SerializedName("fa")
    private String persian;

}
