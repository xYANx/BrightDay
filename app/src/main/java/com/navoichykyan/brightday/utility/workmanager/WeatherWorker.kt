package com.navoichykyan.brightday.utility.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.navoichykyan.brightday.StartApplication

class WeatherWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val loadLocationUseCase = (applicationContext as StartApplication).dependencyFactory.loadLocation()
        loadLocationUseCase.loadLocation()
        return Result.success()
    }

}