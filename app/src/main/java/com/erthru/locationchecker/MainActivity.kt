package com.erthru.locationchecker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    lateinit var lm:LocationManager
    lateinit var location:Location

    var lat:Double? = null
    var lng:Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationHandler()

    }

    private val locationListener = object : LocationListener{
        override fun onLocationChanged(p0: Location?) {
            Toast.makeText(applicationContext,"Location changed",Toast.LENGTH_SHORT).show()
            lat = p0?.latitude
            lng = p0?.longitude

            tvMain.text = "Latitude : $lat \n Longitude : $lng"
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        }

        override fun onProviderEnabled(p0: String?) {
        }

        override fun onProviderDisabled(p0: String?) {
        }

    }

    @SuppressLint("MissingPermission")
    private fun locationHandler(){

        if(!EasyPermissions.hasPermissions(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) || !EasyPermissions.hasPermissions(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){
            EasyPermissions.requestPermissions(this,"Need to access your location",991,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            EasyPermissions.requestPermissions(this,"Need to access your location",992,android.Manifest.permission.ACCESS_FINE_LOCATION)
            recreate()
        }else{
            lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lat = location.latitude
            lng = location.longitude


            tvMain.text = "Latitude : $lat \n Longitude : $lng"

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, locationListener)

        }

    }

}
