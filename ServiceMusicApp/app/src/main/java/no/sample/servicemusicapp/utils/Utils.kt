package no.sample.servicemusicapp.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import androidx.core.app.NotificationCompat
import no.sample.servicemusicapp.MusicActivity
import no.sample.servicemusicapp.R
import no.sample.servicemusicapp.models.Contact
import java.lang.StringBuilder

object Utils {

    /*
    * getContacts returns the list of Contacts save on your device.
    * Note: Implementation details for this function is out of scope for this lecture.
    * */

    fun getContacts(context: Context) : ArrayList<Contact> {

        var contacts = ArrayList<Contact>()

        val cur: Cursor? = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null)

        if ((cur?.getCount() ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val id: String = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name: String = cur.getString(cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME))
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val pCur: Cursor? = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)

                    while (pCur!!.moveToNext()) {
                        val phoneNo: String = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER))

                        contacts.add(Contact(name, phoneNo))
                    }
                    pCur.close()
                }
            }
        }

        return contacts;

    }


    /*
   * getContactsInString returns the string appended all the contacts on your device .
   * */

    fun getContactsInString(context: Context): String
    {
        var stringBuilder = StringBuilder()
        var contacts = getContacts(context)

        contacts.map {
            stringBuilder.appendln("${it.name}   ${it.phone}")
        }

        return stringBuilder.toString()
    }

    /*
   * createNotification creates  a visible notification for a channelId and associate it with a service.
   *  Note: Implementation details for this function is out of scope for this lecture.

   * */

    fun createNotification (sevice: Service, channelId: String) {

        val pendingIntent: PendingIntent =
            Intent(sevice, MusicActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(sevice, 0, notificationIntent, 0)
            }

        val notification: Notification = NotificationCompat.Builder(sevice, channelId)
            .setContentTitle("Notification")
            .setContentText("Music Service")
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentIntent(pendingIntent)
            .setTicker("Playing music")
            .build()

        sevice.startForeground(100, notification)

    }

    /*
  * createNotificationChannel creates  a notification channel for channelId.
  *  Note: Implementation details for this function is out of scope for this lecture.
  * */

    fun createNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= 26) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var channel = NotificationChannel(
                    channelId,
                    "Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
                (context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
            }
        }
    }
}