package com.wojciszke.ryanair.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.wojciszke.ryanair.R
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
                is SearchResults -> showSearchResults()
                is FlightDetails -> showFlightDetails()
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

    private fun showFlightDetails() = supportFragmentManager.apply {
        inTransaction {
            replace(R.id.main_activity_root,
                    findFragmentByTag(FlightDetailsFragment.TAG) ?: FlightDetailsFragment(),
                    FlightDetailsFragment.TAG)
        }
    }
}