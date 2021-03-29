package com.navoichykyan.brightday.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.ResolvableApiException
import com.navoichykyan.brightday.*
import com.navoichykyan.brightday.data.models.Response
import com.navoichykyan.brightday.ui.viewmodel.LocationViewModel
import com.navoichykyan.brightday.ui.viewmodel.MainActivityViewModel
import com.navoichykyan.brightday.utility.Provider
import com.navoichykyan.brightday.utility.extensions.isGPSEnabled
import com.navoichykyan.brightday.utility.extensions.setUrl
import kotlinx.android.synthetic.main.activity_main.*

private const val DEFAULT_CITY = "London"

class MainActivity : AppCompatActivity(),
    BottomNavigationInterface {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var locationViewModel: LocationViewModel

    companion object {
        const val REQUEST_CHECK_SETTINGS = 100
        const val REQUEST_CHECK_PERMISSION = 200
    }

    //НЕ ЗАБЫТЬ ПРО BACKGROUND PERMISSION IN ANDROID 10!!!!!!!
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigation()
        Provider.newInstance().setProvider(ViewModelProvider(this))
        locationViewModel = ViewModelProviders.of(this).get(
            LocationViewModel::class.java
        )

        val worker = (application as StartApplication).dependencyFactory.getWorker()
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(
                    application,
                    worker
                ) as T
            }
        }).get(MainActivityViewModel::class.java)


        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment,
                    WeatherFragment.newInstance(),
                    WeatherFragment.TAG
                )
                .commit()
            checkPermission()


            viewModel.enableLocation.observe(this, Observer {
                it ?: return@Observer
                when (it.status) {
                    Response.Status.LOADING -> println("Loading")
                    Response.Status.SUCCESS -> println("Success")
                    Response.Status.ERROR -> {
                        if (it.error is ResolvableApiException) {
                            try {
                                it.error.startResolutionForResult(
                                    this@MainActivity,
                                    REQUEST_CHECK_SETTINGS
                                )
                            } catch (sendEx: IntentSender.SendIntentException) {

                            }
                        }
                    }
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.startWork()
            } else locationViewModel.selectUrl(
                setUrl(
                    DEFAULT_CITY
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CHECK_PERMISSION) {
            if ((grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
            ) {
                if (application.isGPSEnabled()) {
                    viewModel.startWork()
                } else viewModel.locationSetup()
            }
            else{
                locationViewModel.selectUrl(
                    setUrl(
                        DEFAULT_CITY
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), REQUEST_CHECK_PERMISSION
            )
            return
        } else {
            if (application.isGPSEnabled()) {
                viewModel.startWork()
            } else viewModel.locationSetup()
        }
    }

    private fun setBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuDay -> {
                    openFragment(WeatherFragment.newInstance())
                    true
                }
                R.id.menuForecast -> {
                    openFragment(ForecastFragment.newInstance())
                    true
                }
                R.id.menuCities -> {
                    openFragment(CityFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment,
                fragment,
                fragment.tag
            )
            .commit()
    }

    override fun setFragment() {
        openFragment(WeatherFragment.newInstance())
        bottomNavigation.selectedItemId = R.id.menuDay
    }
}