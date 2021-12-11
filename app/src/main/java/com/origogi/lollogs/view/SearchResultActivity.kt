package com.origogi.lollogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.origogi.lollogs.R
import com.origogi.lollogs.TAG
import com.origogi.lollogs.databinding.ActivitySearchResultBinding
import com.origogi.lollogs.model.Summoner
import com.origogi.lollogs.viewmodel.SearchResultViewModel
import androidx.recyclerview.widget.LinearLayoutManager


class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultBinding
    private val viewModel: SearchResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)
        binding.summoner = Summoner()
        binding.lifecycleOwner = this

        viewModel.summoner.observe(this) {
            if (it.summoner != null) {
                binding.summoner = it.summoner
            }
        }

        binding.tierListview.apply {
            adapter = LeagueListAdapter()
            layoutManager =
                LinearLayoutManager(
                    this@SearchResultActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }

        val summoner = intent.getStringExtra("summoner")!!
        Log.d(TAG, "Summoner : $summoner")

        viewModel.searchData(summoner)
    }
}