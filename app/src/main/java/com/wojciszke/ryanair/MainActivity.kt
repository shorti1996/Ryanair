package com.wojciszke.ryanair

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.wojciszke.ryanair.data.RyanairApi
import com.wojciszke.ryanair.data.RyanairRepository
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.viewmodel.RyanairViewModel
import com.wojciszke.ryanair.viewmodel.RyanairViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var ryanairViewModel: RyanairViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://tripstest.ryanair.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val ryanairApi = retrofit.create(RyanairApi::class.java)

        ryanairViewModel = RyanairViewModelFactory(RyanairRepository(ryanairApi)).create(RyanairViewModel::class.java)

        ryanairViewModel.stations.observe(this) { stations ->
            Log.e("DUPA", stations?.toString() ?: "it's null")
        }

        button_get_stations.setOnClickListener { getStations(ryanairApi) }
    }

    fun getStations(ryanairApi: RyanairApi) {

    }
}
