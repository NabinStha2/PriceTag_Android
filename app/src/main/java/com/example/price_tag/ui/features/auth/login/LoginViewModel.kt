package com.example.price_tag.ui.features.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    init {
        Log.d(TAG, "Init block of LoginViewModel")
    }


    companion object {
        const val TAG = "LoginViewModel"
    }

}