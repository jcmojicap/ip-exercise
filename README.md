# IP EXERCISE

Ejercicio práctico de consulta de información relacionada a una IP. <br>
Provee un endpoint para la consulta de información de un país asociado a una ip y un 
endpoint para la consulta de estadisticas de uso del servicio.

### Puerto

    8890
### Docker
Sobre la raíz del proyecto, ejecutar el comando

    docker build -t ipexercise . 

--------
### API Endpoints

A continuación se listan los endpoints del servicio, así cómo sus respectivos Request y Response
### GET /stats
Muestra la información del país más cercano y el país más lejano a los cuales pertenecen las 
IPs que han sido consultadas, así como el número de invocaciones que ha recibido el servicio y el
promedio de distancia calculado al multiplicar la cantidad de invocaciones desde el país por la 
distancia y sumado a la misma operación con la información del otro país, y el resultado dividido en 
el número total de invocaciones entre los dos paises:

    ((dist_pais1 * invocaciones_pais1) + (dist_pais2 * invocaciones_pais2)) 
    ________________________________________________________________________
                  (invocaciones_pais1 + invocaciones_pais2)
    
####Request:

    <<No recibe parámetros>>
####Response:
Json con la siguiente estructura: 
```
{
    "nearestCountry": {
        "country": "<<Nombre_pais(es)>>",
        "distance": <<Distancia>>,
        "distanceMeasure": "<<Unidad de medida de la distancia>>",
        "invocationsQuantity": <<Cantidad de invocacines>>
    },
    "fartherCountry": {
        "country": "<<Nombre_pais(es)>>",
        "distance": <<Distancia>>,
        "distanceMeasure": "<<Unidad de medida de la distancia>>",
        "invocationsQuantity": <<Cantidad de invocacines>>
    },
    "average": "<<Promedio de la distancia>>"
}
```
Ejemplo:
```
{
    "nearestCountry": {
        "country": "Estados Unidos, ",
        "distance": 9003,
        "distanceMeasure": "KM",
        "invocationsQuantity": 14
    },
    "fartherCountry": {
        "country": "China, ",
        "distance": 18499,
        "distanceMeasure": "KM",
        "invocationsQuantity": 6
    },
    "average": "11851 KM"
}
```
**NOTA:**
*Si la distancia más lejana o más cercada es compartida por más de un país, el servicio mostrará 
los nombres de los paises que comparten la distancia y en la cantidad de invocaciones mostrará 
la suma de invocaciones de los paises invoclucrados.*
--------

### POST /trace
Muestra la información del país al cual pertenece la IP que fue consultada:

####Request:
Json con la siguiente estructura:

    {
        "ip": <<ip a consultar>>
    }
    
Ejemplo:    
    
    {
        "ip": "132.234.1.3"
    }
####Response:
Json con la siguiente estructura: 
```
{
    "ip": "<<ip que fue consultada>>",
    "date": "<<fecha y hora de la consulta>>",
    "country": "<<Nombre en español del país al cual pertenece la IP>>",
    "isoCode": "<<Codigo ISO del país>>",
    "officialLanguages": [
        "<<Lista con los lenguajes oficiales del país>>"
    ],
    "times": [
        "<<Fecha(s) y hora(s) actual(es) en el país consultado>>"
    ],
    "currency": "<<Moneda del país y su tasa de cambio del día a Euros (si está disponible)>>",
    "estimatedDistance": "<<Distancia estimada hasta el punto de referencia>>",
    "distanceMeasure": "<<Unidad de medida hasta el punto de referencia>>"
}
```
Ejemplo:
```
{
    "ip": "132.234.1.3",
    "date": "2021-03-01 03:58:17",
    "country": "Australia",
    "isoCode": "AUS",
    "officialLanguages": [
        "English"
    ],
    "times": [
        "2021-03-01 08:58:17 UTC+05:00",
        "2021-03-01 10:28:17 UTC+06:30",
        "2021-03-01 10:58:17 UTC+07:00",
        "2021-03-01 11:58:17 UTC+08:00",
        "2021-03-01 13:28:17 UTC+09:30",
        "2021-03-01 13:58:17 UTC+10:00",
        "2021-03-01 14:28:17 UTC+10:30",
        "2021-03-01 15:28:17 UTC+11:30"
    ],
    "currency": "AUD (1 EUR = 0.641683 $)",
    "estimatedDistance": "13062",
    "distanceMeasure": "KM"
}
```

###Llamados a través de cURL
Para hacer peticiones a los endpoints, se debe tener en cuenta la siguiente estructura:

Petición GET:
    
    curl -X GET localhost:8890/stats
    
Petición POST: 

    curl -H "Content-Type: application/json" -X POST -d {\"ip\":\"2.3.4.5\"} localhost:8890/trace
    


--------
##Persistencia
Para el almacenado de las estadisticas, se está usando una base de datos relacional con una única tabla

|Country_statistics   |
|---------------------|
|ID                   |
|PAIS                 |
|DISTANCIA            |
|CANTIDAD_INVOCACIONES|


--------
##DEMO
Actualmente, se encuentra un demo desplegado en AWS - ECS en la siguiente URL:<br>

[demo](3.90.227.69:8890/)

--------
## Contacto
Juan Camilo Mojica <jcmojicap@gmail.com>

## Compiling

To compile:

    $ ./gradlew clean build

## Testing

To testing:

    $ ./gradlew test   

## Running

To run:

    $ ./gradlew bootRun

