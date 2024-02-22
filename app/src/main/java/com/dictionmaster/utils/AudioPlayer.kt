package com.dictionmaster.utils

import android.media.MediaPlayer

class AudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(audioUrl: String?, onLoading: (Boolean) -> Unit) {
        if (audioUrl == null) return

        release()

        onLoading(true)
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            prepareAsync()
            setOnPreparedListener {
                onLoading(false)
                it.start()
            }
            setOnErrorListener { _, _, _ ->
                onLoading(false)
                true
            }
            setOnCompletionListener { mp ->
                release()
            }
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
