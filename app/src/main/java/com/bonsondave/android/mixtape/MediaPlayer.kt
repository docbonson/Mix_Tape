package com.bonsondave.android.mixtape


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.*


class MediaPlayer : AppCompatActivity() {

    private lateinit var play: Button
    private lateinit var skip: Button
    private lateinit var previous: Button
    private lateinit var seekbar: SeekBar
    private lateinit var songTitle: TextView
    private lateinit var artist: TextView
    private lateinit var mixName: TextView

    private var mp: MediaPlayer? = null

    private var currentSong: MutableList<Int> = mutableListOf(R.raw.hot_hot_hot, R.raw.heroin_gun, R.raw.just_like_you_just_like_me)

//    var currentSong = listOf<MixTapeData>(MixTapeData("Hot Hot Hot", "Skizzwhores", R.raw.hot_hot_hot),
//            MixTapeData("Just Like You, Just Like Me", "Skizzwhores", R.raw.just_like_you_just_like_me),
//            MixTapeData("Heroin Gun", "Skizzwhores", R.raw.heroin_gun))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player_activity)

        play = findViewById(R.id.btn_play_from_list)
        skip = findViewById(R.id.btn_next)
        previous = findViewById(R.id.btn_previous)
        seekbar = findViewById(R.id.player_SeekBar)
        songTitle = findViewById(R.id.songTitle)
        artist = findViewById(R.id.artist)
        mixName = findViewById(R.id.mix_Name)

        controlSound(currentSong[0])
    }

    //media player button functions
    private fun controlSound(id: Int) {

        play.setOnClickListener {

            if (mp == null){
                mp = MediaPlayer.create(this, id)
                Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            mp?.start()
            Log.d("MainActivity", "Duration: ${mp!!.duration/1000} seconds")
        }

//        buttonPause.setOnClickListener{
//            if(mp != null) mp?.pause()
//            Log.d("MainActivity", "Paused at: ${mp!!.currentPosition/1000} seconds")
//        }


        //Seekbar
        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    private fun initialiseSeekBar() {

        seekbar.max = mp!!.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e:Exception) {
                    seekbar.progress = 0
                }
            }
        }, 0)
    }

    //Menu options
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}