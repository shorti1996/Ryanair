package com.wojciszke.ryanair.view.searchresult

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wojciszke.ryanair.R
import com.wojciszke.ryanair.data.model.app.SearchResult
import kotlinx.android.synthetic.main.flights_list_view_holder.view.*

class SearchResultAdapter(val onItemClickCallback: (SearchResult) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.FlightsListViewHolder>() {
    private val data: MutableList<SearchResult> = mutableListOf()

    fun setData(newData: List<SearchResult>?) {
        data.clear()
        data.addAll(newData ?: listOf())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsListViewHolder =
            FlightsListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.flights_list_view_holder, parent, false))

    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n") // leave it for the purpose of this app
    override fun onBindViewHolder(holder: FlightsListViewHolder, position: Int) {
        val view = holder.itemView
        val itemData = data[position]

        view.vh_date.text = itemData.flightDate
        view.vh_number.text = itemData.flightNumber
        view.vh_duration.text = itemData.flightDuration
        view.vh_price.text = "%.2f %s".format(itemData.regularFarePrice, itemData.currency)

        holder.itemView.setOnClickListener {
            onItemClickCallback(data[position])
        }
    }

    inner class FlightsListViewHolder(view: View) : RecyclerView.ViewHolder(view)
}