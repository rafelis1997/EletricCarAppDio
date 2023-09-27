package com.example.eletriccarappdio.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccarappdio.R
import com.example.eletriccarappdio.databinding.ActivityCalcularAutonomiaBinding

class CalcularAutonomiaActivity: AppCompatActivity() {
    private val binding by lazy { ActivityCalcularAutonomiaBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
        setupCachedResult()
    }

    private fun setupCachedResult() {
        val calculatedValue = getSharedPref()
        binding.tvAutonomiaValue.text = calculatedValue.toString()
    }

    fun setupListeners() {
        binding.btnCalcular.setOnClickListener {
            calcular()
        }

        binding.ivClose.setOnClickListener {
            finish()
        }

    }

    fun calcular() {
        val precoKmValue = binding.etPrecoKwh.text.toString().toFloat()
        val kmPercorridoValue = binding.etKmPercorrido.text.toString().toFloat()

        val autonomiaValor = precoKmValue / kmPercorridoValue

        binding.tvAutonomiaValue.text = autonomiaValor.toString()
        saveSharedPref(autonomiaValor)
    }


    fun saveSharedPref(resultado: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_calc), resultado)
            apply()
        }
    }

    fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_calc), 0.0f)
    }
}