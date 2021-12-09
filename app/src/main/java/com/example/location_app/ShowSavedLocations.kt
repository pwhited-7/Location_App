package com.example.location_app

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class ShowSavedLocations : AppCompatActivity() {

    private lateinit var lvSavedLocations: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_saved_locations)

        lvSavedLocations = findViewById(R.id.lv_wayPoints)

        var myApplication: MyApplication = applicationContext as MyApplication
        var savedLocations: List<Location> = myApplication.getMyLocation()

        lvSavedLocations.adapter = ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, savedLocations)
    }

}