package com.navoichykyan.brightday

import android.app.Application
import com.navoichykyan.brightday.utility.DependencyFactory

class StartApplication: Application() {
    lateinit var dependencyFactory: DependencyFactory
        private set

    override fun onCreate() {
        super.onCreate()
        dependencyFactory =
            DependencyFactory(this)
    }
}