package com.example.eletriccarappdio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarappdio.R
import com.example.eletriccarappdio.data.local.CarRepository
import com.example.eletriccarappdio.domain.Car
import com.example.eletriccarappdio.ui.adapter.CarAdapter

class FavoriteFragment: Fragment() {
    lateinit var listaCarros: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
    }

    override fun onResume() {
        super.onResume()
        setupList()
    }

    private fun getCarsOnLocalDb(): List<Car> {
        val repository = CarRepository(requireContext())

        val carList = repository.getAll()
        return carList
    }

    fun setupView(view: View) {
        listaCarros = view.findViewById(R.id.rv_car_list_favorite)
    }

    fun setupList() {
        val cars = getCarsOnLocalDb()
        val carAdapter = CarAdapter(cars, true)
        listaCarros.apply {
            adapter = carAdapter
            isVisible = true
        }
        carAdapter.carItemListener = { car ->
            val isDeleted = CarRepository(requireContext()).deleteIfExist(car)
            setupList()
        }
    }
}