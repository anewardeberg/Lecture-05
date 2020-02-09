package no.sample.servicemusicapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : AppCompatActivity() {


    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        buttonPermission.setOnClickListener{
            requestContactPermission()
        }
    }

    private fun requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS
                )
        }
        else {
            // Permission has already been granted
            Toast.makeText(this, "Permission has already been granted", Toast.LENGTH_LONG).show()

        }
    }



    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

        }
    }
}
