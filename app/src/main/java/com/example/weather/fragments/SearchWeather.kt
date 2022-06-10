package com.example.weather.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import org.json.JSONObject

class SearchWeather : Fragment() {
    var weather_api = "1395a55568374c5d97143d3bae3473e4"
    private lateinit var searchBar: EditText
    private lateinit var searchButton: Button
    private lateinit var txtCity: TextView
    private lateinit var txtTemp: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_search_weather, container, false)
        searchBar = view.findViewById(R.id.search_bar)
        searchButton = view.findViewById(R.id.search_button)
        txtCity = view.findViewById(R.id.txtWeather)
        txtTemp = view.findViewById(R.id.txtWeatherDescription)

        searchButton.setOnClickListener {
            val city = searchBar.text.toString()
            getWeather(city)
        }
        return view
    }
    @SuppressLint("MissingPermission")
    fun getWeather(city: String) {
        val queue = Volley.newRequestQueue(activity as Context)
        val url: String = "https://api.weatherbit.io/v2.0/current?&city="+city+"&key="+weather_api
        Log.d("url", url)
        val stringReq = StringRequest(Request.Method.GET, url, { response ->
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("data")
            val jsonObject1 = jsonArray.getJSONObject(0)
            val temp = jsonObject1.getString("temp")
            val city = jsonObject1.getString("city_name")
            Log.d("Temp", temp)
            txtCity.text = "Today's weather at "+city+" is:"
            txtTemp.text = temp + "°C"
            txtTemp.visibility = View.VISIBLE
            txtCity.visibility = View.VISIBLE
        }, { error ->
            Toast.makeText(activity as Context, error.message, Toast.LENGTH_SHORT).show()
        })
        queue.add(stringReq)
    }
}