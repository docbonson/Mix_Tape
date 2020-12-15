package com.bonsondave.android.mixtape

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.edit
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class LogInActivity : AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    lateinit var providers : List<AuthUI.IdpConfig>

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.logInTheme)
        //if logout, change theme to recreate() after setTheme
        super.onCreate(savedInstanceState)
            //setContentView(R.layout.activity_main)

        //get the shared preference
        sharedPref = getSharedPreferences(
            getString(R.string.preference_logIn), Context.MODE_PRIVATE
        )

        editor = sharedPref.edit()


        //init
        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(), //Email log in
            AuthUI.IdpConfig.GoogleBuilder().build(), //Google log in
        )


        showSignInOptions()

//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                // ...
//            }

    }

    //Menu options
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.menu_bypass ->
            {
//                val intent = Intent(this, RecyclerList::class.java)
                bypassSignIn()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    //menu ends

    //Build screen
    private fun showSignInOptions() {
        //check if shared preference UID is there
//        if(sharedPref.toString() != "") {
        if(!sharedPref.contains("com.bonsondave.android.mixtape.LOGIN_PREFERENCE")) {

//            bypassSignIn()

//            startActivityForResult(
//                    AuthUI.getInstance().createSignInIntentBuilder()
//                            .setAvailableProviders(providers)
//                            .setLogo(R.drawable.logo)
//                            .setTheme(R.style.logInTheme)
//                            .build(), MY_REQUEST_CODE)
        } else if(sharedPref.toString() != "") {
            bypassSignIn()
        }
        else {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.logo)
                .setTheme(R.style.logInTheme)
                .build(), MY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Companion.MY_REQUEST_CODE) {
            //if logout, change theme to recreate() after setTheme
            editor.remove(resources.getString(R.string.preference_logIn))
            editor.apply()
            showSignInOptions()

        } else if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser // get current user
                Toast.makeText(this, "" + user!!.email, Toast.LENGTH_SHORT).show()

                // store that user has logged into the system
                sharedPref.edit() {
                    putString(getString(R.string.preference_logIn), user.uid)
                    apply()
                }
                bypassSignIn()

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }


    fun bypassSignIn() {
        //start next activity on log in
        val intent = Intent(this, RecyclerList::class.java)
        startActivityForResult(intent, MY_REQUEST_CODE)
        //sliding animation
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    companion object {
        const val MY_REQUEST_CODE: Int = 7117
    }
}