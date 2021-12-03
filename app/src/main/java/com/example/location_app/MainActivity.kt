package com.example.location_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat

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

    lateinit var swLocationUpdates: SwitchCompat
    lateinit var swGps: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


    }
}