package com.example.quiz.data

import android.content.Context
import android.media.MediaPlayer
import com.example.quiz.R

object SoundManager {

    private var correctPlayer: MediaPlayer? = null

    private var wrongPlayer: MediaPlayer? = null

    fun init(context: Context) {
        correctPlayer = MediaPlayer.create(context, R.raw.correct).apply {
            setVolume(1.0f, 1.0f) // 정답 볼륨 (크게)

        }

        wrongPlayer = MediaPlayer.create(context, R.raw.wrong).apply {
            setVolume(0.4f, 0.4f) // 오답 볼륨 (작게)
        }
    }

    fun playCorrect() {
        correctPlayer?.start()
    }

    fun playWrong() {
        wrongPlayer?.start()
    }
}