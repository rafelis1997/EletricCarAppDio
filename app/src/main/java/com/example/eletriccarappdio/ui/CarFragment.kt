package com.example.eletriccarappdio.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.eletriccarappdio.R
import com.example.eletriccarappdio.data.CarsApi
import com.example.eletriccarappdio.data.local.CarRepository

import com.example.eletriccarappdio.domain.Car
import com.example.eletriccarappdio.ui.adapter.CarAdapter
import com.example.eletriccarappdio.databinding.CarFragmentBinding
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

class CarFragment: Fragment() {

    private var _binding: CarFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var carsApi: CarsApi

    var carrosArray: ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CarFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
    }

    override fun onResume() {
        super.onResume()
        if (checkForInternet(context)) {
           // callService() -> outra forma de chamar serviço
            getAllCars()
        } else {
            emptyState()
        }
    }

    fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)
    }

    fun getAllCars() {
        binding.ivEmptyState.isVisible = false
        binding.tvNoInternet.isVisible = false
        carsApi.gatAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if(response.isSuccessful) {
                    binding.pbLoader.isVisible = false
                    response.body()?.let {
                        setupList(it)
                    }
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun emptyState() {
        binding.rvCarList.isVisible = false
        binding.pbLoader.isVisible = false
        binding.ivEmptyState.isVisible = true
        binding.tvNoInternet.isVisible = true
    }


    fun setupList(lista: List<Car>) {
        val carAdapter = CarAdapter(lista)
        binding.rvCarList.apply {
            adapter = carAdapter
            isVisible = true
        }
        carAdapter.carItemListener = { car ->
            val isSaved = CarRepository(requireContext()).saveIfNotExist(car)
        }
    }



    fun callService() {
        val urlBase = "https://igorbag.github.io/cars-api/cars.json"

        MyTask().execute(urlBase)

    }

    fun checkForInternet(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }

        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }

    // Substituido pelo Retrofit
    inner class MyTask: AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            binding.pbLoader.isVisible = true
            binding.ivEmptyState.isVisible = false
            binding.tvNoInternet.isVisible = false
            Log.d("Mytask", "Iniciando...")
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {

                    val model = Car(
                        id = jsonArray.getJSONObject(i).getString("id").toInt(),
                        preco = jsonArray.getJSONObject(i).getString("preco"),
                        bateria = jsonArray.getJSONObject(i).getString("bateria"),
                        potencia = jsonArray.getJSONObject(i).getString("potencia"),
                        urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto"),
                        recarga = jsonArray.getJSONObject(i).getString("recarga"),
                        isFavorite = false,
                    )

                    carrosArray.add(model)
                }
                binding.pbLoader.isVisible = false
               // setupList()
            } catch(ex: Exception) {
                Log.e("Erro", ex.message.toString())
            }
        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null
            try {
                val urlBase = URL(url[0])
                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode

                if(responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use {
                        it.readText()
                    }
                    publishProgress(response)
                } else {
                    Log.e("Erro", "Serviço indisponivel no momento")
                }


            } catch (ex: Exception) {
                Log.e("Erro", "Erro ao realizar processamento")
            } finally {
                urlConnection?.disconnect()
            }

            return " "
        }
    }


}