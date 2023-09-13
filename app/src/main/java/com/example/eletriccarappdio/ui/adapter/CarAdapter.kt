package com.example.eletriccarappdio.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarappdio.R
import com.example.eletriccarappdio.domain.Car

class CarAdapter(private val carros: List<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    //Cria uma nova View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }
    //Pega o conteudo da view e troca pela informacao de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = carros[position].preco
        holder.battery.text = carros[position].bateria
        holder.recharge.text = carros[position].recarga
        holder.potency.text = carros[position].potencia
    }

    //Pega a quantidade de carros da lista
    override fun getItemCount(): Int = carros.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val recharge: TextView
        val potency: TextView
        val urlPhoto: ImageView
        init {
            urlPhoto = view.findViewById(R.id.iv_car)
            price = view.findViewById(R.id.tv_price_value)
            battery = view.findViewById(R.id.tv_battery_value)
            recharge = view.findViewById(R.id.tv_recharge_value)
            potency = view.findViewById(R.id.tv_potency_value)
        }
    }

}

