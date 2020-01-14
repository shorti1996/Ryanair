package com.wojciszke.ryanair

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.wojciszke.ryanair.utils.inTransaction
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchFormViewModel: SearchFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        searchFormViewModel = ViewModelProviders.of(this)[SearchFormViewModel::class.java]

        mainViewModel.currentScreen.observe(this) { screen ->
            when (screen) {
                CurrentScreen.SEARCH_FORM -> showSearchForm()
                CurrentScreen.SEARCH_RESULTS -> showSearchResults()
            }
        }
    }

    private fun showSearchForm() = supportFragmentManager.apply {
        inTransaction {
            replace(R.id.main_activity_root,
                    findFragmentByTag(SearchFormFragment.TAG) ?: SearchFormFragment(),
                    SearchFormFragment.TAG)
        }
    }

    private fun showSearchResults() = supportFragmentManager.apply {
        inTransaction {
            replace(R.id.main_activity_root,
                    findFragmentByTag(SearchResultsFragment.TAG) ?: SearchResultsFragment(),
                    SearchResultsFragment.TAG)
        }
    }
}