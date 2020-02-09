package no.sample.servicemusicapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log

class MusicReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {

            context?.startActivity(Intent(context, MusicActivity::class.java))

        } else if (intent?.action == "android.net.wifi.WIFI_STATE_CHANGED") {

            //WifiManager class provides the primary API for managing all aspects of Wi-Fi connectivity.
            val wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager

            Log.d(this.javaClass.simpleName, "wifi info:" + wifiManager.connectionInfo)

        }
        Log.d(this.javaClass.simpleName, "onReceive: " + intent?.action)


    }
}