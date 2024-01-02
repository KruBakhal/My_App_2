package com.example.newappdi.NewsApp.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

class Constant {
    companion object {
        const val API_KEY = "6953a7421bb749c589e687746c85eee8"
        const val BASE_URL = "https://newsapi.org"
        const val SEARCH_NEWS_TIME_DELAY = 500L

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        const val CODE_OK = "OK"
        const val CODE_ERROR = "ERROR"
        const val RUPEE = "₹ "
        const val TRY_AGAIN = "Please try again."
        const val SOMETHING_WENT_WRONG = "We're sorry for the inconvenience, please try again."
        const val PARSING_WRONG = "We're sorry for the inconvenience, please try again."
        const val NO_CONNECTION = "Internet unavailable."
    }

}