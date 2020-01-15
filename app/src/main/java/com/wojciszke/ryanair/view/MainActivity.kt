package com.wojciszke.ryanair.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.wojciszke.ryanair.R
import com.wojciszke.ryanair.data.model.app.SearchFormData
import com.wojciszke.ryanair.data.model.app.SearchResult
import com.wojciszke.ryanair.utils.inTransaction
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.view.flightdetails.FlightDetailsFragment
import com.wojciszke.ryanair.view.searchform.SearchFormFragment
import com.wojciszke.ryanair.view.searchresult.SearchResultsFragment
import com.wojciszke.ryanair.viewmodel.*

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
                is SearchForm -> showSearchForm()
                is SearchResults -> showSearchResults(screen.searchFormData)
                is FlightDetails -> showFlightDetails(screen.searchResult)
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

    private fun showSearchResults(searchFormData: SearchFormData) = supportFragmentManager.apply {
        inTransaction {
            replace(R.id.main_activity_root,
                    findFragmentByTag(SearchResultsFragment.TAG) ?: SearchResultsFragment.newInstance(searchFormData),
                    SearchResultsFragment.TAG)
            addToBackStack(null)
        }
    }

    private fun showFlightDetails(searchResult: SearchResult) = supportFragmentManager.apply {
        inTransaction {
            replace(R.id.main_activity_root,
                    findFragmentByTag(FlightDetailsFragment.TAG) ?: FlightDetailsFragment.newInstance(searchResult),
                    FlightDetailsFragment.TAG)
            addToBackStack(null)
        }
    }
}