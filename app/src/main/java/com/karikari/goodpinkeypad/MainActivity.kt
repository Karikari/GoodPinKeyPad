package com.karikari.goodpinkeypad

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //MaterialButton back_dark = findViewById(R.id.bac);
        val keyPad = findViewById<GoodPinKeyPad>(R.id.key)
        val typeface = ResourcesCompat.getFont(this, R.font.play_fair)
        keyPad.setTypeFace(typeface)
        keyPad.setErrorText("All be lies")

        //keyPad.setBackPressView(back_dark);

        keyPad.setErrorIndicators(true)
        keyPad.setKeyPadListener( object : KeyPadListerner {
            override fun onKeyPadPressed(value: String?) {
                Log.d(TAG, "Key pressed : $value")

                //keyPad.setErrorIndicators(true)
            }

            override fun onKeyBackPressed() {

            }

            override fun onClear() {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}