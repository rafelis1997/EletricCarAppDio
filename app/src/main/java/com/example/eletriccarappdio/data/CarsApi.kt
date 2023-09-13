package com.example.eletriccarappdio.data

import com.example.eletriccarappdio.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {
    @GET("cars.json")
    fun gatAllCars(): Call<List<Car>>
}