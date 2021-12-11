package com.origogi.lollogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.origogi.lollogs.R
import com.origogi.lollogs.TAG
import com.origogi.lollogs.databinding.ActivitySearchResultBinding
import com.origogi.lollogs.viewmodel.SearchResultViewModel

class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchResultBinding
    private val viewModel : SearchResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)

        supportActionBar?.hide()
        val summoner = intent.getStringExtra("summoner")!!
        Log.d(TAG, "Summoner : $summoner")

        viewModel.searchData(summoner)
    }
}