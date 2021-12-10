package com.origogi.lollogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.origogi.lollogs.R
import com.origogi.lollogs.TAG

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        supportActionBar?.hide()
        val summoner = intent.getStringExtra("summoner")!!
        Log.d(TAG, "Summoner : $summoner")
    }
}