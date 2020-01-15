package com.wojciszke.ryanair.view.flightdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wojciszke.ryanair.R
import com.wojciszke.ryanair.networking.FlightsRepository
import com.wojciszke.ryanair.model.SearchResult
import com.wojciszke.ryanair.di.component.DaggerSearchFlightsComponent
import kotlinx.android.synthetic.main.fragment_flight_details.*
import javax.inject.Inject

class FlightDetailsFragment : Fragment() {
    @Inject
    lateinit var flightsRepository: FlightsRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerSearchFlightsComponent.create().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_flight_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<SearchResult>(SEARCH_RESULT_KEY)?.let { showFlightDetails(it) }
    }

    private fun showFlightDetails(searchResult: SearchResult) {
        with(searchResult) {
            text_view_details_origin_value.text = origin
            text_view_details_destination_value.text = destination
            text_view_details_infants_left_value.text = infantsLeft.toString()
            text_view_details_fare_class_value.text = fareClass
            text_view_details_discount_percent_value.text = discountInPercent.toString()
        }
    }

    companion object {
        const val TAG = "flight-details-fragment-tag"

        private const val SEARCH_RESULT_KEY = "search-result-key"

        fun newInstance(searchResult: SearchResult) = FlightDetailsFragment().apply {
            arguments = Bundle().apply {
                this.putParcelable(SEARCH_RESULT_KEY, searchResult)
            }
        }
    }
}