package com.example.eletriccarappdio.data.local

import android.provider.BaseColumns

object CarsContract {

    object CarEntry: BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_CAR_ID = "id"
        const val COLUMN_NAME_PRECO = "price"
        const val COLUMN_NAME_BATERIA = "bateria"
        const val COLUMN_NAME_POTENCIA = "potencia"
        const val COLUMN_NAME_RECARGA = "recarga"
        const val COLUMN_NAME_URL_PHOTO = "url_photo"
    }

    const val TABLE_CAR =
        "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_CAR_ID} VARCHAR(255)," +
                "${CarEntry.COLUMN_NAME_PRECO} VARCHAR(255)," +
                "${CarEntry.COLUMN_NAME_BATERIA} VARCHAR(255)," +
                "${CarEntry.COLUMN_NAME_POTENCIA} VARCHAR(255)," +
                "${CarEntry.COLUMN_NAME_RECARGA} VARCHAR(255)," +
                "${CarEntry.COLUMN_NAME_URL_PHOTO} TEXT)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"
}