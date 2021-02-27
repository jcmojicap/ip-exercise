package com.meli.ipexercise.services.helper;

import com.meli.ipexercise.models.Coordenate;
import org.springframework.stereotype.Service;

@Service
public class DistanceBuilder {

    public static double RADIO_TIERRA = 6371.0;
    public static double LATITUD_BS_AS = -34.6037;
    public static double LONGITUD_BS_AS = -58.3816;

    public static synchronized double distance(Coordenate origen, Coordenate destino){
        double diferenciaLatitud = inRadians(destino.getLatitud() - origen.getLatitud());
        double diferenciaLongitud = inRadians(destino.getLongitud() - origen.getLongitud());

        double a = Math.pow(Math.sin(diferenciaLatitud/2),2) + Math.cos(inRadians(origen.getLatitud())) *
                Math.cos(inRadians(destino.getLatitud())) * Math.pow(Math.sin(diferenciaLongitud/2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA * c;
    }

    public static synchronized double inRadians(double value){
        return (Math.PI/180) * value;
    }
}
