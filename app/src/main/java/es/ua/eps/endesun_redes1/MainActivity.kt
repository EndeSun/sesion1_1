package es.ua.eps.endesun_redes1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.location.FusedLocationProviderClient
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (checkLocationPermission()) {
            requestLocation()
        }
    }
    //Para obtener los permisos del usuario antes de acceder a la ubicación
    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION
            )
            return false
        }
        return true
    }
    //Para acceder a la ubicación del dispositivo
    private fun requestLocation() {
        //Si no está permitido rompe
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        //Si todo correcto, accedemos
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            location?.let {
                val latitude = location.latitude
                val longitude = location.longitude
                val latitud = findViewById<TextView>(R.id.latitud)
                val longitud = findViewById<TextView>(R.id.longitud)
                latitud.text = "$latitude"
                longitud.text = "$longitude"
            }
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_LOCATION = 1
    }
}