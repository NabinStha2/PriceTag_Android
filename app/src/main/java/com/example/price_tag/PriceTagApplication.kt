package com.example.price_tag

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PriceTagApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Coming inside price tag application on create")
    }

    companion object {
        const val TAG = "PriceTagApplication"
    }
}