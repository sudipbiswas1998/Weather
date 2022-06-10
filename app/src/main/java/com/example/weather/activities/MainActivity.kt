package com.example.weather.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.example.weather.fragments.CurrentWeather
import com.example.weather.fragments.FavoritesWeather
import com.example.weather.fragments.SearchWeather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val firstFragment = CurrentWeather()
        val secondFragment = SearchWeather()
        val thirdFragment = FavoritesWeather()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_current_weather -> setCurrentFragment(firstFragment)

                R.id.action_search -> {
                    setCurrentFragment(secondFragment)
                }
                R.id.action_favorites -> {
                    setCurrentFragment(thirdFragment)
                }
            }
            true
        }

    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame,fragment)
            commit()
        }
}