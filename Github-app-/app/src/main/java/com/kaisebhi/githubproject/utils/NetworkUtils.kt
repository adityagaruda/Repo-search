package com.kaisebhi.githubproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtils {
    companion object {
        val TAG = "NetworkUtil.kt"
        fun getNetworkState(context: Context): Boolean {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
                val network = connectivityManager.activeNetwork
                val networkProperties = connectivityManager.getNetworkCapabilities(network)
                val isCellular = networkProperties?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                val isWifi = networkProperties?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//                Log.d(TAG, "getNetworkState: $isWifi , $isCellular , $networkProperties")
                if(networkProperties != null && isCellular != null && isWifi != null) {
                    return isCellular || isWifi
                }
            }
            return false
        }
    }
}