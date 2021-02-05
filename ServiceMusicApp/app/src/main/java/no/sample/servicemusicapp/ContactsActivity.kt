package no.sample.servicemusicapp

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import no.sample.servicemusicapp.databinding.ActivityPermissionBinding


class ContactsActivity : AppCompatActivity() {

    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100
    lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPermission.setOnClickListener{
            readContactList()
        }

        registerReceiver(wifiStateReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))

    }

    private fun readContactList() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                binding.textView.text = getString(R.string.promting_message)

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS
                )
        }
        else
        {
            binding.textView.text = Utils.getContactsInString(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {

            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    binding.textView.text = Utils.getContactsInString(this)
                } else {
                    binding.textView.text = getString(R.string.permission_denied)
                }
            }
        }
    }


    private val wifiStateReceiver : BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent)
        {

            when(intent?.action )
            {
                WifiManager.WIFI_STATE_CHANGED_ACTION->
                {
                    val wifiStateExtra = intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN)

                    when (wifiStateExtra) {

                        WifiManager.WIFI_STATE_ENABLED -> {
                            binding.textView.text = "Wifi is ON :)"
                        }
                        WifiManager.WIFI_STATE_DISABLED -> {
                            binding.textView.text = "Wifi is Off :("
                        }

                    }
                }
            }
         }
    }
}
