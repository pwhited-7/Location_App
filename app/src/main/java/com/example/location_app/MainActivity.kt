package com.example.location_app

import android.app.UiModeManager.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest

class MainActivity : AppCompatActivity() {

    // UI elements
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvAltitude: TextView
    private lateinit var tvAccuracy: TextView
    private lateinit var tvSpeed: TextView
    private lateinit var tvSensor: TextView
    private lateinit var tvUpdates: TextView
    private lateinit var tvAddress: TextView

    // Switches
    lateinit var swLocationUpdates: SwitchCompat
    lateinit var swGps: SwitchCompat

    // API for location services
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest


    val fastUpdateInterval: Long = 5
    val defaultUpdateInterval: Long = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        tvAltitude = findViewById(R.id.tvAltitude)
        tvAccuracy = findViewById(R.id.tvAccuracy)
        tvSpeed = findViewById(R.id.tvSpeed)
        tvSensor = findViewById(R.id.tvSensor)
        tvUpdates = findViewById(R.id.tvUpdates)
        tvAddress = findViewById(R.id.tvAddress)
        swLocationUpdates = findViewById(R.id.swLocationsUpdates)
        swGps = findViewById(R.id.swGps)

        locationRequest = LocationRequest()

        locationRequest.interval = 1000 * defaultUpdateInterval
        locationRequest.fastestInterval = 1000 * fastUpdateInterval
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        swGps.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(swGps.isChecked){
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                tvSensor.text = "Using GPS sensors"
            }
            else{
                locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                tvSensor.text = "Using Towers + Wifi"
            }
        }


    }
}