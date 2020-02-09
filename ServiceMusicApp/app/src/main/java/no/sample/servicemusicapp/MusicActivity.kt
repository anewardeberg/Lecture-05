package no.sample.servicemusicapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : AppCompatActivity() {


    var receiver : MusicPlayerReceiver? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_music)


        //Register and start a local receiver
        registerAndStartMusicReceiver()


        // start a Music service
        var intent = Intent(this@MusicActivity, MusicService::class.java)
        intent.putExtra(Common.EXTRA_COMMAND, Common.COMMAND_QUERY) // ask for a states.
        startService(intent)


        buttonPlay.setOnClickListener{

            // start a Music service with play command
            var intent = Intent(this@MusicActivity, MusicService::class.java)
            intent.putExtra(Common.EXTRA_COMMAND, Common.COMMAND_PLAY)
            startService(intent)

            buttonPlay.text = "Playing..."
        }

        buttonStop.setOnClickListener{

            // stop service
            var intent = Intent(this@MusicActivity, MusicService::class.java)
            stopService(intent)

            buttonPlay.text = "Play"
        }


        Log.d(this.javaClass.simpleName, "onCreate")

    }

    //If a receiver is not declared in the manifest it can be
    //programmatically registered and started as follows
    private fun registerAndStartMusicReceiver() {

        receiver = MusicPlayerReceiver()

        var intentFilter = IntentFilter() // Create an intent filter

        // and add action STRINGS which you want to handle
        intentFilter.addAction(Common.ACTION_PLAYING)
        intentFilter.addAction(Common.ACTION_STOPPED)

        registerReceiver(
            receiver,
            intentFilter
        ) // registerReceiver will start the receiver to listen for action events
    }

    override fun onResume() {
        super.onResume()
        Log.d(this.javaClass.simpleName, "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d(this.javaClass.simpleName, "onPause")
    }


    override fun onStop() {
        super.onStop()
        Log.d(this.javaClass.simpleName, "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this.javaClass.simpleName, "onDestroy")

        unregisterReceiver(receiver) // unregister running MusicPlayerReceiver which was created during on create


    }



    inner class MusicPlayerReceiver: BroadcastReceiver(){

        override fun onReceive(context: Context?, intent: Intent?) {

            if( intent?.action == Common.ACTION_PLAYING) {
                buttonPlay.text = "Playing..."
            }

            if( intent?.action == Common.ACTION_STOPPED) {
                buttonPlay.text = "Play"
            }

            Log.d(this.javaClass.simpleName, intent?.action)
        }
    }

}
