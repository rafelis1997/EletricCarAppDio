package com.example.eletriccarappdio.data

import com.example.eletriccarappdio.domain.Car

object CarFactory {
    val list = listOf<Car>(
        Car(
            id = 1,
            price = "R$ 300.000,00",
            potency = "200 cv",
            battery = "300 kWh",
            recharge = "30 min",
            urlPhoto = "www.google.com"
        ),
        Car(
            id = 2,
            price = "R$ 500.000,00",
            potency = "235 cv",
            battery = "320 kWh",
            recharge = "40 min",
            urlPhoto = "www.google.com"
        ),
        Car(
            id = 3,
            price = "R$ 200.000,00",
            potency = "250 cv",
            battery = "370 kWh",
            recharge = "60 min",
            urlPhoto = "www.google.com"
        ),
    )
}