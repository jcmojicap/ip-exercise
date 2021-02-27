package com.meli.ipexercise.services.helper;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Utils {

    public static synchronized String formatDistance(double distance){
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(distance);
    }
}
