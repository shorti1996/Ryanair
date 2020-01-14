package com.wojciszke.ryanair

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.wojciszke.ryanair.data.FlightsRepository
import com.wojciszke.ryanair.data.StationsRepository
import com.wojciszke.ryanair.di.component.DaggerSearchFlightsComponent
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.viewmodel.*
import javax.inject.Inject

class SearchResultsFragment : Fragment() {
    @Inject
    lateinit var flightsRepository: FlightsRepository

    private lateinit var searchFormViewModel: SearchFormViewModel
    private lateinit var searchResultsViewModel: SearchResultsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerSearchFlightsComponent.create().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareViewModels()
    }

    private fun prepareViewModels() {
        searchFormViewModel = ViewModelProviders.of(activity!!)[SearchFormViewModel::class.java]
        searchFormViewModel.canTriggerSearch.observe(this) {
            it?.let { if (it) Unit else Unit }
        }
        searchResultsViewModel = ViewModelProviders.of(this, SearchResultsViewModelFactory(flightsRepository))[SearchResultsViewModel::class.java]
        searchResultsViewModel.stations.observe(this) {
            it?.trips?.forEach { trip ->
                Log.e("FLIGHTS", trip.toString())
            }
        }
    }

    companion object {
        const val TAG = "search-results-fragment-tag"
    }
}