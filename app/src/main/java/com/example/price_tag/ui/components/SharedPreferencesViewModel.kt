package com.example.price_tag.ui.components


import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedPreferencesViewModel @Inject constructor(private val sharedPref: SharedPreferences) :
    ViewModel() {
    private val TAG = "SharedPreferencesUtil"
    private val _firstTimeInApp = "firstTimeInApp"
    private val _bearerToken = "bearerToken"
    private val _refreshToken = "refreshToken"
    private val _userId = "userId"

    val firstTimeInApp: String = _firstTimeInApp
    val bearerToken: String = _bearerToken
    val refreshToken: String = _refreshToken
    val userId: String = _userId


    //To Store String data
    fun save(KEY_NAME: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()
    }

    //To Store Int data
    fun save(KEY_NAME: String, value: Int) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()
    }

    //To Store Floating data
    fun save(KEY_NAME: String, value: Float) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putFloat(KEY_NAME, value)
        editor.apply()
    }

    //To Store Boolean data
    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status)
        editor.apply()
    }

    //To Retrieve Int
    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    // To Retrieve Boolean
    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    //To Retrieve String
    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueFloat(KEY_NAME: String): Float {
        return sharedPref.getFloat(KEY_NAME, 0.0f)
    }

    // To clear all data
    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(userId)
        editor.remove(bearerToken)
        editor.apply()
    }

    fun removeAllSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    // To remove a specific data
    fun removeValue(KEY_NAME: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }
}