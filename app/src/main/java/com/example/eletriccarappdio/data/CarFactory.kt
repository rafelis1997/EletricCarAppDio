package com.example.eletriccarappdio.data

import com.example.eletriccarappdio.domain.Car

object CarFactory {
    val list = listOf<Car>(
        Car(
            id = 1,
            preco = "R$ 300.000,00",
            potencia = "200 cv",
            bateria = "300 kWh",
            recarga = "30 min",
            urlPhoto = "www.google.com"
        ),
        Car(
            id = 2,
            preco = "R$ 500.000,00",
            potencia = "235 cv",
            bateria = "320 kWh",
            recarga = "40 min",
            urlPhoto = "www.google.com"
        ),
        Car(
            id = 3,
            preco = "R$ 200.000,00",
            potencia = "250 cv",
            bateria = "370 kWh",
            recarga = "60 min",
            urlPhoto = "www.google.com"
        ),
    )
}