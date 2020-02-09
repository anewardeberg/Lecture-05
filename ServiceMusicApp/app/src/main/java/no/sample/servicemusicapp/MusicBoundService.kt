package no.sample.servicemusicapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MusicBoundService : Service() {

    var mediaPlayer : MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return MusicPlayerBinder()
    }


    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.sound)

        Log.d(this.javaClass.simpleName, "onCreate")

    }



    inner class MusicPlayerBinder : Binder(){

       fun isPlaying(): Boolean? {
           return  this@MusicBoundService.mediaPlayer?.isPlaying
       }

        fun play() {
            this@MusicBoundService.mediaPlayer?.start()
        }

        fun stop() {
            this@MusicBoundService.mediaPlayer?.pause()
        }
    }


    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(this.javaClass.simpleName, "onRebind")

    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(this.javaClass.simpleName, "onDestroy")

    }


}


