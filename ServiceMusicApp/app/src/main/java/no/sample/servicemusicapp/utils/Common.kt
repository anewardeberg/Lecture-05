package no.sample.servicemusicapp.utils

class Common {

    // Kotlin way to have static variables

    companion object { // In Kotlin anything member declared in companion object is statuc

        const val ACTION_PLAYING : String = "musicapp.music.playing"
        const val ACTION_STOPPED : String  = "musicapp.music.stopped"

        const val EXTRA_COMMAND : String = "COMMAND"

        const val COMMAND_QUERY : Int = 1
        const val COMMAND_PLAY : Int = 2

    }
}