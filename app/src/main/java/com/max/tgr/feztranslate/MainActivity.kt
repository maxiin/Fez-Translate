package com.max.tgr.feztranslate

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.main_pager
import kotlinx.android.synthetic.main.activity_main.main_tab

class MainActivity : AppCompatActivity() {

    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f

        main_pager.adapter = Adapter(supportFragmentManager)
        main_tab.setupWithViewPager(main_pager)
        main_tab.bringToFront()

        main_tab.setOnClickListener { v ->
            Log.e("v", v.toString())
        }

        prefs = this.getSharedPreferences("prefsFile", 0)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.controller, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val send = prefs!!.edit()

        when (item.itemId) {

            R.id.menu_controller_xbox -> {
                item.isChecked = true
                send.putString("Controller", "xbox").apply()
                return true
            }
            R.id.menu_controller_playstation -> {
                item.isChecked = true
                send.putString("Controller", "play").apply()
                return true
            }
            R.id.menu_controller_pc -> {
                item.isChecked = true
                send.putString("Controller", "pc").apply()
                return true
            }
            R.id.menu_gh ->{
                startActivity(Intent(ACTION_VIEW, Uri.parse("https://github.com/MaxTgr/Fez-Translate")))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

}
