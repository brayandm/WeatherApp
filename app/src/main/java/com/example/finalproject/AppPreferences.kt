package com.example.finalproject

import android.content.Context

val appPreferences: AppPreferences by lazy {
    AppApplication.appPreferences!!
}

class AppPreferences(context: Context) {

    private val preferences = context.getSharedPreferences("", Context.MODE_PRIVATE)

    fun setDarkMode(value: Boolean) {
        with (preferences.edit()) {
            putBoolean("DarkMode", value)
            apply()
        }
    }

    fun setFahrenheit(value: Boolean) {
        with (preferences.edit()) {
            putBoolean("Fahrenheit", value)
            apply()
        }
    }

    fun getDarkMode(): Boolean {
        return preferences.getBoolean("DarkMode", false)
    }

    fun getFahrenheit(): Boolean {
        return preferences.getBoolean("Fahrenheit", false)
    }
}