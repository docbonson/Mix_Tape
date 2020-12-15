package com.bonsondave.android.mixtape

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recycler_list.*

class RecyclerList : AppCompatActivity(), MixTapeRecyclerAdapter.OnItemClickListener {

    private val itemList = generateDummyList(1)
    private val adapter = MixTapeRecyclerAdapter(itemList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_list)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
        val clickedItem: MixTapeData = itemList[position]
        val intent = Intent(this, MediaPlayer::class.java)
        startActivity(intent)
    }

    private fun generateDummyList(size: Int): List<MixTapeData> {
        val list = ArrayList<MixTapeData>()

        val item = MixTapeData("Dave's Mix", "Hot Hot Hot", "Skizzwhores", R.raw.hot_hot_hot)
        list += item
        return list
    }

    //Menu options
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Eventually add logic to turn off music
        when (item.itemId) {
            R.id.menu_signOut -> {
                //will want to eventually move this to first activity(LogInActivity)
                //Check to see if logged in
//                FirebaseAuth.getInstance().signOut()

//                val intent = Intent(this, LogInActivity::class.java)
                val data: Intent = Intent()
//                data.putExtra(EXTRA_MESSAGE, passBack)
                setResult(MY_REQUEST_CODE, data)

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //MAKE GLOBAL
    companion object {
        const val MY_REQUEST_CODE: Int = 7117
    }
}