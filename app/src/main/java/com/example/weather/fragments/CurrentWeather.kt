package com.example.weather.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONObject


class CurrentWeather : Fragment() {

    var weather_url = ""
    var weather_api = "1395a55568374c5d97143d3bae3473e4"
    private lateinit var txtCity: TextView
    private lateinit var txtTemp: TextView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_current_weather, container, false)
        txtTemp = view.findViewById(R.id.txtTemp)
        txtCity = view.findViewById(R.id.txtCity)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(activity as Context)
        getWeather()

        return view
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
                Toast.makeText(activity as Context, "No location found", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun getTemp(){
        val queue = Volley.newRequestQueue(activity as Context)
        val url: String = weather_url
        Log.d("url", url)
        val stringReq = StringRequest(Request.Method.GET, url, { response ->
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("data")
            val jsonObject1 = jsonArray.getJSONObject(0)
            val temp = jsonObject1.getString("temp")
            val city = jsonObject1.getString("city_name")
            Log.d("Temp", temp)
            txtCity.text = "Today's weather at "+city+" is:"
            txtTemp.text = temp + "??C"
            txtTemp.visibility = View.VISIBLE
            txtCity.visibility = View.VISIBLE
        }, { error ->
            Toast.makeText(activity as Context, error.message, Toast.LENGTH_SHORT).show()
        })
        queue.add(stringReq)
    }


}

