package com.navoichykyan.brightday.utility

import androidx.lifecycle.ViewModelProvider

class Provider {
    fun setProvider(_provider : ViewModelProvider){
        provider = _provider
    }

    fun getProvider() =
        provider

    companion object {
        lateinit var provider : ViewModelProvider
        fun newInstance() =
            Provider()
    }
}