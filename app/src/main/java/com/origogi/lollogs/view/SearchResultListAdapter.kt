package com.origogi.lollogs.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.origogi.lollogs.R
import com.origogi.lollogs.TAG
import com.origogi.lollogs.databinding.ListItemMatchBinding
import com.origogi.lollogs.databinding.ListItemSummaryBinding
import com.origogi.lollogs.databinding.ListItemSummonerBinding
import com.origogi.lollogs.model.*
import java.lang.Exception

class SearchResultListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SUMMONER = 0
        const val SUMMARY = 1
        const val GAME_DATA = 2
    }

    private var summoner: Summoner? = null
    private var summary: RecentGameSummaryData? = null
    private var gameDataList: List<GameData> = emptyList()

    private var listItems: List<out ListType> = emptyList()

    fun updateSummoner(summoner: Summoner) {
        this.summoner = summoner

        updateList()
    }

    fun updateSummary(summary: RecentGameSummaryData) {
        Log.d(TAG, summary.summary.toString())
        this.summary = summary

        updateList()
    }

    private fun updateList() {
        listItems = mutableListOf<ListType>().apply {
            summoner?.let { add(it) }
            summary?.let { add(it) }
            gameDataList?.let {
                addAll(it)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (listItems[position]) {
            is Summoner -> SUMMONER
            is RecentGameSummaryData -> SUMMARY
            else -> GAME_DATA
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SUMMONER -> {
                val binding = DataBindingUtil.inflate<ListItemSummonerBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_summoner, parent, false
                )
                binding.tierListview.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = LeagueListAdapter()
                }
                SummonerViewHolder(binding)

            }
            SUMMARY -> {
                val binding = DataBindingUtil.inflate<ListItemSummaryBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_summary, parent, false
                )
                SummaryViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ListItemMatchBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_match, parent, false
                )
                MatchViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder).bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun updateGameList(gameList : List<GameData>) {
        this.gameDataList = gameList
        updateList()
    }

    private interface Binder {
        fun bind(data: ListType)
    }

    private class SummonerViewHolder(private val binding: ListItemSummonerBinding) :
        RecyclerView.ViewHolder(binding.root), Binder {

        override fun bind(data: ListType) {
            binding.summoner = data as Summoner
            binding.executePendingBindings()
        }
    }

    private class SummaryViewHolder(private val binding: ListItemSummaryBinding) :
        RecyclerView.ViewHolder(binding.root), Binder {

        override fun bind(data: ListType) {
            binding.data = data as RecentGameSummaryData
            binding.executePendingBindings()
        }
    }

    private class MatchViewHolder(private val binding: ListItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root), Binder {

        override fun bind(data: ListType) {
            binding.game = data as GameData
            binding.executePendingBindings()
        }
    }
}