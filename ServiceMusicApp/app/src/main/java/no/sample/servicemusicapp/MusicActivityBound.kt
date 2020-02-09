package no.sample.servicemusicapp

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivityBound : AppCompatActivity() , ServiceConnection {

    var musicPlayerBinder: MusicBoundService.MusicPlayerBinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)


        startService(Intent(this@MusicActivityBound, MusicBoundService::class.java))


        if(musicPlayerBinder == null) {
            bindService(Intent(this@MusicActivityBound, MusicBoundService::class.java), this, Service.BIND_AUTO_CREATE)
        }

        buttonPlay.setOnClickListener{


            if (musicPlayerBinder?.isPlaying()==false){
                musicPlayerBinder?.play()
                buttonPlay.text = getString(R.string.playing)
            }
        }

        buttonStop.setOnClickListener{


            if(musicPlayerBinder?.isPlaying()==true){
                musicPlayerBinder?.stop()
                buttonPlay.text = getString(R.string.play)
            }
        }


        Log.d(this.javaClass.simpleName, "onCreate")

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

        unbindService(this)

        Log.d(this.javaClass.simpleName, "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()


        Log.d(this.javaClass.simpleName, "onDestroy")

    }

    override fun onServiceDisconnected(componentName: ComponentName?) {

    }

    override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {

        musicPlayerBinder = binder as MusicBoundService.MusicPlayerBinder

        if(musicPlayerBinder?.isPlaying() == true){
            buttonPlay.text = getString(R.string.playing)
        }
        else{
            buttonPlay.text = getString(R.string.play)
        }

    }
}
