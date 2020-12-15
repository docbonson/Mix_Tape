package com.bonsondave.android.mixtape


import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipData.Item
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import kotlinx.android.synthetic.main.activity_media_player_activity.*


class MediaPlayer : AppCompatActivity() {

    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player_activity)

        mp = MediaPlayer.create(this, R.raw.hot_hot_hot)
        mp.isLooping = true
        totalTime = mp.duration

        // seek bar
        player_SeekBar.max = totalTime
        player_SeekBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                    ) {
                        if (fromUser) {
                            mp.seekTo(progress)
                        }
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                }
        )

        //Thread
        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                }
            }
        }).start()

    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            //update position bar
            player_SeekBar.progress = currentPosition

            //update labels
            var elapsedTime = createTimeLabel(currentPosition)
            textCurrentTime.text = elapsedTime

            var remainingTimeLabel = createTimeLabel(totalTime - currentPosition)
            textTotalTime.text = "-$remainingTimeLabel"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLable = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLable = "$min"
        if (sec < 10) timeLable += "0"
        timeLable += sec

        return timeLable
    }

    //media player button functions
    fun btnPlay(view: View) {
        if (mp.isPlaying) {
            //stop
            mp.pause()
            btn_play.setBackgroundResource(R.drawable.ic_play)
            stopAnimation()

        } else {
            //start
            mp.start()
            btn_play.setBackgroundResource(R.drawable.ic_pause)
            startAnimation()

        }
    }

    //Menu options
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        val signOut: Item = findViewById(R.id.menu_signOut)
    }

    //Animation to spin the vinyl image
    private fun startAnimation() {
        val spin = AnimationUtils.loadAnimation(this,R.anim.anim_rotate)
        rightSpindle.startAnimation(spin)
        leftSpindle.startAnimation(spin)
    }

    private fun stopAnimation() {
        rightSpindle.clearAnimation()
        leftSpindle.clearAnimation()
    }



}