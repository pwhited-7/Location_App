package com.example.location_app

import android.app.Application
import android.location.Location

class MyApplication: Application() {

    private lateinit var singleton: MyApplication

    private lateinit var myLocations: List<Location>

    fun getMyLocation():List<Location>{
        return myLocations
    }

    fun setMyLocations(myLocations: List<Location>){
        this.myLocations = myLocations
    }

    fun getInstance(): MyApplication{
        return singleton
    }

    override fun onCreate(){
        super.onCreate()
        singleton = this
        myLocations = arrayListOf()
    }



}