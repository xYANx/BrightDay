package com.navoichykyan.brightday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.navoichykyan.brightday.R
import com.navoichykyan.brightday.StartApplication
import com.navoichykyan.brightday.databinding.FragmentWeatherBinding
import com.navoichykyan.brightday.ui.viewmodel.LocationViewModel
import com.navoichykyan.brightday.ui.viewmodel.SharedViewModel
import com.navoichykyan.brightday.ui.viewmodel.WeatherViewModel


class WeatherFragment() : Fragment(){
    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weather, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = ViewModelProviders.of(activity!!).get(
            SharedViewModel::class.java
        )
        val locationModel = ViewModelProviders.of(activity!!).get(
            LocationViewModel::class.java
        )
        val loadWeatherDataUseCase = (requireActivity().application as StartApplication).dependencyFactory.loadWeatherDataUseCase()
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return WeatherViewModel(
                    loadWeatherDataUseCase
                ) as T
            }
        }).get(WeatherViewModel::class.java)
        if(savedInstanceState == null) {
            viewModel.setVisibility(View.INVISIBLE)

            locationModel.observableUrl.observe(this, Observer {
                viewModel.load(it)
            })

            viewModel.weatherModel.observe(this, Observer {
                model.select(it)
            })
        }
    }
    companion object {
        const val TAG = "WeatherListFragment"
        fun newInstance() =
            WeatherFragment()
    }
}