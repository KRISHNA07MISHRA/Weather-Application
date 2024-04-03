package com.example.myweatherapp

import MyWeatherData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myweatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import weatherinterface

class MainActivity : AppCompatActivity() {

    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchData()


    }


    private fun fetchData(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(weatherinterface::class.java)


        val response = retrofit.getWeather("metric","noida","239d979c25317796f5a1ea7ac5d38f30")
        response.enqueue(object :Callback<MyWeatherData>{
            override fun onResponse(call: Call<MyWeatherData>, response: Response<MyWeatherData>) {
                val responsebody = response.body()!!
                if(response.isSuccessful){
                    val temperature = responsebody.main.temp.toString()
                    binding.temp.text = "$temperature °C"
                    val mintemp = responsebody.main.temp_min.toString()
                    val maxtemp = responsebody.main.temp_max.toString()
                    val humidity = responsebody.main.humidity.toString()
                    val wind  = responsebody.wind.speed.toString()
                    val condition = responsebody.weather.firstOrNull()?.main?:"unknown"



                    binding.mintemp.text = "Min temp: $mintemp °C"
                    binding.maxtemp.text = "Max temp: $maxtemp °C"
                    binding.humidity.text = "$humidity %"
                    binding.windspeed.text = "$wind m/s"
                    binding.weather.text = "$condition "
                    binding.city.text = "noida"

                //    Log.d("TAG","onResponse: $temperature")
                }
                else {
                    Toast.makeText(this@MainActivity, "Failed to get weather data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MyWeatherData>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Failed to connect to the server",Toast.LENGTH_LONG).show()
            }

        })
    }
}