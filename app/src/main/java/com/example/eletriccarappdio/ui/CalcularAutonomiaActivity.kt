package com.example.eletriccarappdio.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eletriccarappdio.R

class CalcularAutonomiaActivity: AppCompatActivity() {
    lateinit var precoKm: EditText
    lateinit var kmPercorrido: EditText
    lateinit var btnCalcular: Button
    lateinit var btnClose: ImageView
    lateinit var autonomia: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        setupView()
        setupListeners()
        setupCachedResult()
    }

    private fun setupCachedResult() {
        val calculatedValue = getSharedPref()
        autonomia.text = calculatedValue.toString()
    }

    fun setupView() {
        precoKm = findViewById(R.id.et_preco_kwh)
        kmPercorrido = findViewById(R.id.et_km_percorrido)
        autonomia = findViewById(R.id.tv_autonomia_value)
        btnCalcular = findViewById(R.id.btn_calcular)
        btnClose = findViewById(R.id.iv_close)
    }

    fun setupListeners() {
        btnCalcular.setOnClickListener {
            calcular()
        }

        btnClose.setOnClickListener {
            finish()
        }

    }

    fun calcular() {
        val precoKmValue = precoKm.text.toString().toFloat()
        val kmPercorridoValue = kmPercorrido.text.toString().toFloat()

        val autonomiaValor = precoKmValue / kmPercorridoValue

        autonomia.text = autonomiaValor.toString()
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