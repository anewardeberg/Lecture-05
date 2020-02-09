package no.sample.servicemusicapp

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MusicService : Service() {

    var mediaPlayer : MediaPlayer? = null // MediaPlayer instance variable that can run audio files.


    override fun onBind(intent: Intent): IBinder? {
        return null // returns null because this is a unbound service
    }

    override fun onCreate() {
        super.onCreate()


        mediaPlayer = MediaPlayer.create(
            this,
            R.raw.sound
        ) // Media player instance is created and it loads the sound.mp3 file from res/raw/sound.mp3


        Log.d(this.javaClass.simpleName, "onCreate")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        //Reading the command from intent
        var command = intent?.getIntExtra(Common.EXTRA_COMMAND, Common.COMMAND_PLAY)


        when(command)
        {
            Common.COMMAND_PLAY->
                if( mediaPlayer?.isPlaying!! == false ) {
                    mediaPlayer?.start() // start the media player if it is not already running
                }

            Common.COMMAND_QUERY->

            if( mediaPlayer?.isPlaying!! ) {
                sendBroadcast(Intent(Common.ACTION_PLAYING))
            }
            else {
                sendBroadcast(Intent(Common.ACTION_STOPPED))
            }
        }

        Log.d(this.javaClass.simpleName, "onStartCommand")

        return START_NOT_STICKY // tells the android system not to bother to restart the service, even when it has sufficient memory.

    }



    override fun onDestroy() {
        super.onDestroy()

        if( mediaPlayer?.isPlaying!! ) {
            mediaPlayer?.stop() // Service is being destroyed therefore it must stop running mediaPlayer
            sendBroadcast(Intent(Common.ACTION_STOPPED))

        }

        Log.d(this.javaClass.simpleName, "onDestroy")


    }
}


