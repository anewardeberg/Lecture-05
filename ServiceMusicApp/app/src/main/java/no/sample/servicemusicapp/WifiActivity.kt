package no.sample.servicemusicapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import no.sample.servicemusicapp.databinding.ActivityWifiBinding

class WifiActivity : AppCompatActivity() {

    lateinit var binding : ActivityWifiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(wifiStateReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))

    }

    private val wifiStateReceiver : BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent)
        {

            when(intent.action)
            {
                WifiManager.WIFI_STATE_CHANGED_ACTION->
                {
                    val wifiStateExtra = intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN)

                    when (wifiStateExtra) {

                        WifiManager.WIFI_STATE_ENABLED -> {
                            binding.imageWifi.setImageResource(R.drawable.ic_wifi_on)
                        }
                        WifiManager.WIFI_STATE_DISABLED -> {
                            binding.imageWifi.setImageResource(R.drawable.ic_wifi_off)
                        }

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(wifiStateReceiver)

    }
}