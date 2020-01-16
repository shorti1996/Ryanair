package com.wojciszke.ryanair.view.searchresult

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.wojciszke.ryanair.R
import com.wojciszke.ryanair.RyanairApplication
import com.wojciszke.ryanair.di.component.DaggerFlightsAvailabilityComponent
import com.wojciszke.ryanair.di.component.DaggerRyanairRepositoryComponent
import com.wojciszke.ryanair.model.SearchFormData
import com.wojciszke.ryanair.model.SearchResult
import com.wojciszke.ryanair.repository.FlightsRepository
import com.wojciszke.ryanair.utils.observe
import com.wojciszke.ryanair.utils.setActionBarTitleOrDefault
import com.wojciszke.ryanair.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search_results.*
import javax.inject.Inject

class SearchResultsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel: MainViewModel by lazy { ViewModelProviders.of(activity!!)[MainViewModel::class.java] }
    private val searchResultsViewModel: SearchResultsViewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[SearchResultsViewModel::class.java]  }

    private val searchResultAdapter by lazy { SearchResultAdapter(::onSearchItemClicked) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFlightsAvailabilityComponent.builder().ryanairRepositoryComponent(
                (requireActivity().application as RyanairApplication).ryanairRepositoryComponent
        ).build().inject(this)
    }

    override fun onResume() {
        super.onResume()
        searchResultsViewModel.onFocusedFlightChanged(null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_search_results, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareViewModels()
        prepareViews()
    }

    override fun onDetach() {
        searchResultsViewModel.onSearchFormChanged(null)
        setActionBarTitle(null)
        super.onDetach()
    }

    private fun prepareViews() {
        with(recycler_view_search_results) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchResultAdapter
        }

        with(requireActivity().seek_bar_price) {
            setMaxPrice(progress)
            setOnSeekBarChangeListener(SeekBarListener { seekBar -> setMaxPrice(seekBar.progress) })
        }
    }

    private fun setMaxPrice(maxPrice: Int) {
        searchResultsViewModel.onMaxPriceChanged(maxPrice)
    }

    private fun onSearchItemClicked(searchResult: SearchResult) {
        mainViewModel.onCurrentScreenChanged(FlightDetails(searchResult))
        searchResultsViewModel.onFocusedFlightChanged(searchResult)
    }

    private fun prepareViewModels() {
        with(searchResultsViewModel) {
            searchResults.observe(this@SearchResultsFragment) {
                updateProgressBar(it)
                searchResultAdapter.setData(it)
            }
            originName.observe(this@SearchResultsFragment) {
                setActionBarTitle(origin = it)
            }
            destinationName.observe(this@SearchResultsFragment) {
                setActionBarTitle(destination = it)
            }
            onSearchFormChanged(arguments?.getParcelable(SEARCH_FORM_DATA_KEY))
        }
    }

    private fun updateProgressBar(it: List<SearchResult>?) {
        progress_bar_search_flights.visibility = if (it == null) View.VISIBLE else View.GONE
    }

    private fun setActionBarTitle(
            origin: String? = searchResultsViewModel.originName.value,
            destination: String? = searchResultsViewModel.destinationName.value) {
        requireActivity().setActionBarTitleOrDefault(if (origin != null && destination != null) prepareActionBarTitle(origin, destination) else null)
    }

    private fun prepareActionBarTitle(origin: String, destination: String) = "$origin -> $destination"

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