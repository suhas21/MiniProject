package com.example.suhas.mini1
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
class ConnectivityHelper {
    fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info=connectivity.activeNetworkInfo
        if (info != null) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
        }
        return false
    }

}