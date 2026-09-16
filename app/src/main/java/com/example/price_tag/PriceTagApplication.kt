package com.example.price_tag

import android.app.Application
import android.util.Log
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PriceTagApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,
              "Coming inside price tag application on create")

        PaymentConfiguration.init(
            context = this,
            publishableKey = BuildConfig.STRIPE_PUBLISHABLE_KEY,
        )
    }

    companion object {
        const val TAG = "PriceTagApplication"
    }
}