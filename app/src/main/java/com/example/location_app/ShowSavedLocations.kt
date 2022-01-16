package com.example.location_app

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import java.lang.Exception

class ShowSavedLocations : AppCompatActivity() {

    private lateinit var lvSavedLocations: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_saved_locations)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF6200EE")))
        supportActionBar?.title = "Waypoint List"

        lvSavedLocations = findViewById(R.id.lv_wayPoints)

        var myApplication: MyApplication = applicationContext as MyApplication
        var savedLocations: List<Location> = myApplication.getMyLocation()
        var filteredLocations: MutableList<String> = mutableListOf<String>()



        savedLocations.forEach{ location: Location ->

            val geocoder = Geocoder(this)

            val address: String = try {
                val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                addresses[0].getAddressLine(0)
            }catch (e: Exception){
                "Unable to get street address"
            }

            val str: String = "Latitude: " + location.latitude+ "     Longitude: " + location.longitude +"\n" +
                    "Address: " + address

            filteredLocations.add(str)
        }


        lvSavedLocations.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filteredLocations)
    }

}