package com.wojciszke.ryanair

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.wojciszke.ryanair.data.StationsRepository
import com.wojciszke.ryanair.data.model.app.StationAutocomplete
import com.wojciszke.ryanair.data.model.app.toStationAutocomplete
import com.wojciszke.ryanair.data.model.stations.Stations
import com.wojciszke.ryanair.di.component.DaggerSearchFlightsComponent
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.viewmodel.StationsViewModel
import com.wojciszke.ryanair.viewmodel.FlightSearchViewModelFactory
import com.wojciszke.ryanair.viewmodel.SearchFormViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var stationsRepository: StationsRepository

    private lateinit var stationsViewModel: StationsViewModel
    private lateinit var searchFormViewModel: SearchFormViewModel

    private val autocompleteOriginAdapter by lazy { createEmptyArrayAdapter() }
    private val autocompleteDestinationAdapter by lazy { createEmptyArrayAdapter() }
    private val multiAutocomplete by lazy { listOf(autocompleteOriginAdapter, autocompleteDestinationAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerSearchFlightsComponent.create().inject(this)

        prepareViewModel()
        prepareAutocompleteTextViews()
        prepareDateTextView()
    }

    private fun prepareDateTextView() {
        edit_text_flight_date.addTextChangedListener {
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    searchFormViewModel.onDateChanged(s?.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // stub
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // stub
                }
            }
        }
    }

    private fun createEmptyArrayAdapter() =
            ArrayAdapter<StationAutocomplete>(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())

    private fun prepareViewModel() {
        stationsViewModel = ViewModelProviders.of(this, FlightSearchViewModelFactory(stationsRepository))[StationsViewModel::class.java]
        searchFormViewModel = ViewModelProviders.of(this)[SearchFormViewModel::class.java]

        with(stationsViewModel) {
            stations.observe(this@MainActivity) { stations -> onStationsChanged(stations) }
        }
        with(searchFormViewModel) {
            canTriggerSearch.observe(this@MainActivity) { canTriggerSearch -> onCanTriggerSearchChanged(canTriggerSearch) }
        }

    }

    private fun onStationsChanged(stations: Stations?) {
        stations?.stations?.map { it.toStationAutocomplete() }?.let { autocompleteItems ->
            multiAutocomplete.forEach { adapter ->
                adapter.clear()
                adapter.addAll(autocompleteItems)
            }
        }
    }

    private fun onCanTriggerSearchChanged(canTriggerSearch: Boolean) {
        if (canTriggerSearch) button_search_flights.setOnClickListener { Toast.makeText(this@MainActivity, "OK", Toast.LENGTH_SHORT).show() }
        else button_search_flights.setOnClickListener { Toast.makeText(this@MainActivity, "Please, fill the form", Toast.LENGTH_SHORT).show() }
    }

    private fun prepareAutocompleteTextViews() {
        text_view_flight_origin.apply {
            setAdapter(autocompleteOriginAdapter)
            setOnItemClickListener { _, _, position, _ ->
                autocompleteOriginAdapter.getItem(position)?.code?.let { searchFormViewModel.onOriginSelected(it) }
            }
        }
        text_view_flight_destination.apply {
            setAdapter(autocompleteDestinationAdapter)
            setOnItemClickListener { _, _, position, _ ->
                autocompleteDestinationAdapter.getItem(position)?.code?.let { searchFormViewModel.onDestinationSelected(it) }
            }
        }
    }
}