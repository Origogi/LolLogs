package com.origogi.lollogs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.origogi.lollogs.R
import com.origogi.lollogs.TAG
import com.origogi.lollogs.databinding.ActivitySearchResultBinding
import com.origogi.lollogs.viewmodel.SearchResultViewModel
import androidx.recyclerview.widget.LinearLayoutManager


class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultBinding
    private val viewModel: SearchResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val summoner = intent.getStringExtra("summoner")!!
        Log.d(TAG, "Summoner : $summoner")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.recyclerView.apply {
            adapter = SearchResultListAdapter({
                viewModel.searchData(summoner)
            }, {
                viewModel.loadMore()
            })
            layoutManager =
                LinearLayoutManager(
                    this@SearchResultActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }

        viewModel.errorMessage.observe(this, { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                if (binding.recyclerView.adapter?.itemCount == 0) {
                    finish()
                }
            }
        })

        viewModel.searchData(summoner)
    }
}