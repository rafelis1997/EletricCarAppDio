package com.example.eletriccarappdio.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.eletriccarappdio.data.local.CarsContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.eletriccarappdio.data.local.CarsContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.eletriccarappdio.data.local.CarsContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.eletriccarappdio.data.local.CarsContract.CarEntry.COLUMN_NAME_PRECO
import com.example.eletriccarappdio.data.local.CarsContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.eletriccarappdio.data.local.CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.example.eletriccarappdio.data.local.CarsContract.CarEntry.TABLE_NAME
import com.example.eletriccarappdio.domain.Car

class CarRepository(private val context: Context) {
    fun save(car: Car): Boolean {
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, car.id)
                put(COLUMN_NAME_PRECO, car.preco)
                put(COLUMN_NAME_POTENCIA, car.potencia)
                put(COLUMN_NAME_BATERIA, car.bateria)
                put(COLUMN_NAME_RECARGA, car.recarga)
                put(COLUMN_NAME_URL_PHOTO, car.urlPhoto)
            }

            val inserted = db?.insert(TABLE_NAME, null, values)

            if(inserted != null) {
                isSaved = true
            }
        } catch (ex: Exception) {
            ex.message?.let {
                Log.e("Erro ao inserir dados", it)
            }
        }

        return isSaved
    }

    fun delete(car: Car): Boolean {
        var isDeleted = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val filter = "$COLUMN_NAME_CAR_ID = ?"
            val filterValues = arrayOf(car.id.toString())

            val deleted = db?.delete(TABLE_NAME, filter, filterValues)

            if(deleted != null) {
                isDeleted = true
            }

        } catch (ex: Exception) {
            ex.message?.let {
                Log.e("Erro ao inserir dados", it)
            }
        }

        return isDeleted
    }

    fun findCarById(id: Int): Car {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        //Lista das colunas a serem exibidas no resultado da query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,
            null,
        )

        var itemId: Long = 0
        var preco = ""
        var bateria = ""
        var recarga = ""
        var urlPhoto = ""
        var potencia = ""
        with(cursor) {
            while (moveToNext()) {
                itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
            }
        }
        cursor.close()
        return Car(
            id = itemId.toInt(),
            preco = preco,
            bateria = bateria,
            recarga = recarga,
            potencia = potencia,
            urlPhoto = urlPhoto,
            isFavorite = true,
        )
    }

    fun saveIfNotExist(car: Car) {
        val hasCar = findCarById((car.id))
        if(hasCar.id == ID_WHEN_NO_CAR) {
            save(car)
        }
    }

    fun deleteIfExist(car: Car) {
        val hasCar = findCarById((car.id))
        if(hasCar != null) {
            delete(car)
        }
    }

    companion object {
        const val ID_WHEN_NO_CAR = 0
    }

    fun getAll() : List<Car> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        //Lista das colunas a serem exibidas no resultado da query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )

        val cursor = db.query(
            TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null,
        )

        val cars = mutableListOf<Car>()

        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                val preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                val bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                val recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                val potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))

                cars.add(
                    Car(
                        id = itemId.toInt(),
                        preco = preco,
                        bateria = bateria,
                        recarga = recarga,
                        potencia = potencia,
                        urlPhoto = urlPhoto,
                        isFavorite = true,
                    )
                )
            }
        }
        cursor.close()
        return cars
    }


}