package com.wojciszke.ryanair.view.searchresult

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wojciszke.ryanair.R
import com.wojciszke.ryanair.networking.FlightsRepository
import com.wojciszke.ryanair.data.model.app.SearchFormData
import com.wojciszke.ryanair.data.model.app.SearchResult
import com.wojciszke.ryanair.di.component.DaggerSearchFlightsComponent
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.viewmodel.*
import kotlinx.android.synthetic.main.fragment_search_results.*
import javax.inject.Inject

class SearchResultsFragment : Fragment() {
    @Inject
    lateinit var flightsRepository: FlightsRepository

    private val mainViewModel: MainViewModel by lazy { ViewModelProviders.of(activity!!)[MainViewModel::class.java] }
    private lateinit var searchResultsViewModel: SearchResultsViewModel

    private val searchResultAdapter by lazy { SearchResultAdapter(::onSearchItemClicked) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerSearchFlightsComponent.create().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(recycler_view_search_results) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchResultAdapter
        }
        prepareViewModels()
    }

    private fun prepareViewModels() {
        searchResultsViewModel = ViewModelProviders.of(activity!!, SearchResultsViewModelFactory(flightsRepository))[SearchResultsViewModel::class.java]
        searchResultsViewModel.searchResults.observe(this) {
            searchResultAdapter.setData(it)
        }
        searchResultsViewModel.onSearchFormChanged(arguments?.getParcelable(SEARCH_FORM_DATA_KEY))
    }

    override fun onDetach() {
        searchResultsViewModel.onSearchFormChanged(null)
        super.onDetach()
    }

    private fun onSearchItemClicked(searchResult: SearchResult) {
        mainViewModel.onCurrentScreenChanged(FlightDetails(searchResult))
        searchResultsViewModel.onFocusedFlightChanged(searchResult)
    }

    companion object {
        const val TAG = "search-results-fragment-tag"

        private const val SEARCH_FORM_DATA_KEY = "search-form-data-key"

        fun newInstance(searchFormData: SearchFormData) = SearchResultsFragment().apply {
            arguments = Bundle().apply {
                this.putParcelable(SEARCH_FORM_DATA_KEY, searchFormData)
            }
        }
    }
}