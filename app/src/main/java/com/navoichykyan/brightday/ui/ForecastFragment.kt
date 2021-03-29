package com.navoichykyan.brightday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navoichykyan.brightday.R
import com.navoichykyan.brightday.ui.viewmodel.SharedViewModel
import com.navoichykyan.brightday.ui.adapters.WeatherAdapter
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment() : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_forecast, container, false)

    override fun onResume() {
        super.onResume()
        initWeatherList()
        val model = ViewModelProviders.of(activity!!).get(
            SharedViewModel::class.java
        )
        if(model.getSelected()?.value != null) {
            (viewWeatherList.adapter as WeatherAdapter).updateItemList(model.getSelected()!!.value!!)
        }
    }

    private fun initWeatherList() {
        viewWeatherList.apply {
            adapter =
                WeatherAdapter()
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    companion object {
        const val TAG = "ForecastFragment"
        fun newInstance() =
            ForecastFragment()
    }
}