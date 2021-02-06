package no.sample.servicemusicapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import no.sample.servicemusicapp.databinding.ActivityPermissionBinding
import no.sample.servicemusicapp.utils.Utils


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
    }

    private fun readContactList() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

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
        permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    binding.textView.text = Utils.getContactsInString(this)
                } else {
                    binding.textView.text = getString(R.string.permission_denied)
                }
            }
        }
    }
}
