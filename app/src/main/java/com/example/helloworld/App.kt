package com.example.helloworld

import android.app.Application
import androidx.room.Room

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    val db: MyDatabase by lazy {
        Room.databaseBuilder(
            this,
            MyDatabase::class.java,
            "database"
        ).fallbackToDestructiveMigration(true).allowMainThreadQueries().build()
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }
}