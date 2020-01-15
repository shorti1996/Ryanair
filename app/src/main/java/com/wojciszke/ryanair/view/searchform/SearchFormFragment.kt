package com.wojciszke.ryanair.view.searchform

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wojciszke.ryanair.R
import com.wojciszke.ryanair.model.StationAutocomplete
import com.wojciszke.ryanair.model.toStationAutocomplete
import com.wojciszke.ryanair.di.component.DaggerSearchFlightsComponent
import com.wojciszke.ryanair.networking.StationsRepository
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.utils.registerOnNetworkAvailableCallback
import com.wojciszke.ryanair.utils.unregisterOnNetworkAvailableCallback
import com.wojciszke.ryanair.viewmodel.*
import kotlinx.android.synthetic.main.fragment_search_form.*
import kotlinx.android.synthetic.main.fragment_search_form.view.*
import javax.inject.Inject

class SearchFormFragment : Fragment() {
    @Inject
    lateinit var stationsRepository: StationsRepository

    private val mainViewModel: MainViewModel by lazy { ViewModelProviders.of(activity!!)[MainViewModel::class.java] }
    private lateinit var stationsViewModel: StationsViewModel
    private lateinit var searchFormViewModel: SearchFormViewModel

    private val autocompleteOriginAdapter by lazy { createEmptyArrayAdapter() }
    private val autocompleteDestinationAdapter by lazy { createEmptyArrayAdapter() }
    private val multiAutocomplete by lazy { listOf(autocompleteOriginAdapter, autocompleteDestinationAdapter) }

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_form, container, false).apply {
                this.edit_text_flight_date.addTextChangedListener { editable -> searchFormViewModel.onDateChanged(editable?.toString()) }
                this.edit_text_adults.addTextChangedListener { editable -> searchFormViewModel.onAdultsChanged(editable?.toString()) }
                this.edit_text_teens.addTextChangedListener { editable -> searchFormViewModel.onTeensChanged(editable?.toString()) }
                this.edit_text_children.addTextChangedListener { editable -> searchFormViewModel.onChildrenChanged(editable?.toString()) }
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerSearchFlightsComponent.create().inject(this)

        prepareViewModels()
        prepareAutocompleteTextViews()
        requireContext().registerOnNetworkAvailableCallback {
            stationsViewModel.reloadStations()
        }?.let { networkCallback = it }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkCallback?.let { requireContext().unregisterOnNetworkAvailableCallback(it) }
    }

    private fun createEmptyArrayAdapter() =
            ArrayAdapter<StationAutocomplete>(requireContext(), android.R.layout.simple_dropdown_item_1line, mutableListOf())

    private fun prepareViewModels() {
        stationsViewModel = ViewModelProviders.of(this, StationsViewModelFactory(stationsRepository))[StationsViewModel::class.java]
        searchFormViewModel = ViewModelProviders.of(activity!!)[SearchFormViewModel::class.java]

        with(stationsViewModel) {
            stations.observe(this@SearchFormFragment) { stations -> onStationsChanged(stations) }
        }
        with(searchFormViewModel) {
            searchFormData.observe(this@SearchFormFragment) { Log.d(TAG, it.toString()) }
            canTriggerSearch.observe(this@SearchFormFragment) { canTriggerSearch -> onCanTriggerSearchChanged(canTriggerSearch) }
        }

    }

    private fun onStationsChanged(stations: com.wojciszke.core.model.stations.Stations?) {
        stations?.stations?.map { it.toStationAutocomplete() }?.let { autocompleteItems ->
            multiAutocomplete.forEach { adapter ->
                adapter.clear()
                adapter.addAll(autocompleteItems)
            }
        }
    }

    private fun onCanTriggerSearchChanged(canTriggerSearch: Boolean) {
        if (canTriggerSearch) button_search_flights.setOnClickListener {
            Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
            mainViewModel.onCurrentScreenChanged(SearchResults(searchFormViewModel.searchFormData.value!!))
        }
        else button_search_flights.setOnClickListener { Toast.makeText(requireContext(), "Please, fill the form", Toast.LENGTH_SHORT).show() }
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

    companion object {
        const val TAG = "search-form-fragment-tag"
    }
}