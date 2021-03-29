package com.navoichykyan.brightday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.navoichykyan.brightday.ui.viewmodel.CitiesViewModel
import com.navoichykyan.brightday.R
import com.navoichykyan.brightday.StartApplication
import com.navoichykyan.brightday.data.db.CityEntity
import com.navoichykyan.brightday.ui.adapters.CitiesListAdapter
import com.navoichykyan.brightday.ui.viewmodel.LocationViewModel
import com.navoichykyan.brightday.utility.extensions.setUrl
import kotlinx.android.synthetic.main.fragment_city.*

class CityFragment : Fragment(),
    CitiesListAdapter.TouchClickListener {
    private lateinit var viewModel: CitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_city, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loadCiDataUseCase = (requireActivity().application as StartApplication).dependencyFactory.loadCitiesDataUseCase()
        val insertCityUseCase = (requireActivity().application as StartApplication).dependencyFactory.insertCityUseCase()
        val deleteCityUseCase = (requireActivity().application as StartApplication).dependencyFactory.deleteCityUseCase()
        val db = (requireActivity().application as StartApplication).dependencyFactory.getDataBase()
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CitiesViewModel(
                    loadCiDataUseCase,
                    insertCityUseCase,
                    deleteCityUseCase,
                    db
                ) as T
            }
        }).get(CitiesViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        initCitiesList()
        viewModel.getCities()
        saveCityButton.setOnClickListener {
            viewModel.insertCity(addCityText.text.toString())
            addCityText.text.clear()
        }
        viewModel.citiesList.observe(this, Observer {
            (viewCitiesList.adapter as? CitiesListAdapter)?.updateItemList(it)
        })
    }

    private fun initCitiesList() {
        viewCitiesList.apply {
            adapter =
                CitiesListAdapter(this@CityFragment)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onClickDelete(cityEntity: CityEntity) {
        viewModel.deleteCity(cityEntity)
    }

    override fun onClickTouch(cityEntity: CityEntity) {
        val locationModel = ViewModelProviders.of(activity!!).get(
            LocationViewModel::class.java
        )
        locationModel.selectUrl(
            setUrl(
                cityEntity.city
            )
        )
        (activity as BottomNavigationInterface).setFragment()
    }

    companion object {
        const val TAG = "CityFragment"
        fun newInstance() =
            CityFragment()
    }

}