package com.example.weather.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var weather_url = ""
    var weather_api = "1395a55568374c5d97143d3bae3473e4"
    private lateinit var textView: TextView
    private lateinit var txtWeather : TextView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.txtWeatherDescription)
        txtWeather = findViewById(R.id.txtWeather)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        getWeather()

    }
    @SuppressLint("MissingPermission")
    fun getWeather(){
        Log.e("lat", "function")
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if(location != null){
                weather_url = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location?.latitude + "&lon=" + location?.longitude + "&key=" + weather_api
                Log.e("lat", weather_url.toString())
                getTemp()
            }
            else{
                Toast.makeText(this, "No location found", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getTemp(){
        val queue = Volley.newRequestQueue(this)
        val url: String = weather_url
        Log.d("url", url)
        val stringReq = StringRequest(Request.Method.GET, url, { response ->
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("data")
            val jsonObject1 = jsonArray.getJSONObject(0)
            val temp = jsonObject1.getString("temp")
            val city = jsonObject1.getString("city_name")
            Log.d("Temp", temp)
            txtWeather.text = "Today's weather at "+city+" is:"
            textView.text = temp + "°C"
        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        })
        queue.add(stringReq)
    }

}