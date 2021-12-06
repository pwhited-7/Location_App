package com.example.location_app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.lang.Exception
import java.util.jar.Manifest

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
    private lateinit var swLocationUpdates: SwitchCompat
    private lateinit var swGps: SwitchCompat

    // Buttons
    private lateinit var btnNewWayPoint: Button
    private lateinit var btnShowWayPoints: Button

    // API for location services
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val fastUpdateInterval: Long = 5
    private val defaultUpdateInterval: Long = 30
    private val permissionsCode: Int = 99

    // Current Location
    private lateinit var currentLocation: Location

    // List of saved locations
    private lateinit var savedLocations: List<Location>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF6200EE")))

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
        btnNewWayPoint = findViewById(R.id.btn_newWayPoint)
        btnShowWayPoints = findViewById(R.id.btn_showWayPoints)



        locationRequest =  LocationRequest()

        locationRequest.interval = 1000 * defaultUpdateInterval
        locationRequest.fastestInterval = 1000 * fastUpdateInterval
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                updateUIValues(locationResult.lastLocation)
            }
        }

        btnNewWayPoint.setOnClickListener {
            // Get GPS Location

            // Add the new location to the list
        }

        swGps.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                tvSensor.text = "Using GPS sensors"
            }
            else{
                locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                tvSensor.text = "Using Towers + Wifi"
            }
        }

        swLocationUpdates.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                startLocationUpdates()
            }
            else{
                stopLocationUpdates()
            }
        }

        updateGPS()

    } // end onCreate

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        tvUpdates.text = "Location is being tracked"

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        updateGPS()
    }

    private fun stopLocationUpdates() {
        tvUpdates.text = "Location is NOT being tracked"
        tvLatitude.text = "Location is NOT being tracked"
        tvLongitude.text = "Location is NOT being tracked"
        tvSpeed.text = "Location is NOT being tracked"
        tvAddress.text = "Location is NOT being tracked"
        tvAccuracy.text = "Location is NOT being tracked"
        tvAltitude.text = "Location is NOT being tracked"
        tvSensor.text = "Location is NOT being tracked"

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            permissionsCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    updateGPS()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(
                        this,
                        "This app requires permission to be granted in order to work properly",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                return
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests
            }
        }
    }




    private fun updateGPS() {
        //Get permission
        //Get current location
        //Update the UI

        val permissionFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        when (PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(
                this,
                permissionFine) -> {
                //user provides permission
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location ->
                    // we got permission
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT)
                        .show()
                    updateUIValues(location)
                }
            }
            else -> {
                // You can directly ask for the permission.
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                        arrayOf(permissionFine),
                        permissionsCode
                    )
                }
            }
        }

    }



    private fun updateUIValues(location: Location) {

        tvLatitude.text = location.latitude.toString()
        tvLongitude.text = location.longitude.toString()
        tvAccuracy.text = location.accuracy.toString()

        tvAltitude.text = if(location.hasAltitude()){
            location.altitude.toString()
        } else{
            "Not available"
        }

        tvSpeed.text = if(location.hasSpeed()){
            location.speed.toString()
        } else{
            "Not available"
        }

        val geocoder = Geocoder(this)

        try {
            val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            tvAddress.text = addresses[0].getAddressLine(0)
        }catch (e: Exception){
            tvAddress.text = "Unable to get street address"
        }

    }
}