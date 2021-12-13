package com.origogi.lollogs.view

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.origogi.lollogs.R
import com.origogi.lollogs.databinding.ListItemMatchBinding
import com.origogi.lollogs.databinding.ListItemSummaryBinding
import com.origogi.lollogs.databinding.ListItemSummonerBinding
import com.origogi.lollogs.model.*
import kotlinx.coroutines.*

class SearchResultListAdapter(
    private val doRefresh: () -> Unit,
    private val doLoadMore: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SUMMONER = 0
        const val SUMMARY = 1
        const val GAME_DATA = 2
    }

    private val throttle = Throttle(1000L)

    private var listItems: List<ListType> = emptyList()

    fun update(newListItems: List<ListType>) {
        val diffCallback = Diff(listItems, newListItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listItems = newListItems

        diffResult.dispatchUpdatesTo(this)
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
                binding.refresh.setOnClickListener {
                    doRefresh()
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

        if (position > itemCount - 10) {
            throttle.launch {
                doLoadMore()
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
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
            binding.gameMatch = data as GameMatch
            binding.executePendingBindings()
        }
    }

    private class Diff(
        private val oldItems: List<ListType>,
        private val newItems: List<ListType>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int =
            oldItems.size

        override fun getNewListSize(): Int =
            newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            if (oldItems[oldItemPosition] is Summoner && newItems[newItemPosition] is Summoner) {
                return true
            }

            if (oldItems[oldItemPosition] is RecentGameSummaryData && newItems[newItemPosition] is RecentGameSummaryData) {
                return true
            }

            if (oldItems[oldItemPosition] is GameMatch && newItems[newItemPosition] is GameMatch) {
                return (oldItems[oldItemPosition] as GameMatch).gameId == (newItems[newItemPosition] as GameMatch).gameId
            }
            return false
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }


    private class Throttle(
        private val delayMs: Long,
        private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        private var job: Job? = null
        private var nextMs: Long = 0
        fun launch(func: () -> Unit) {
            val current = SystemClock.uptimeMillis()
            if (nextMs < current) {
                nextMs = current + delayMs
                func()
                job?.cancel()
            } else {
                job?.cancel()
                job = coroutineScope.launch {
                    delay(nextMs - current)
                    nextMs = SystemClock.uptimeMillis() + delayMs
                    func()
                }
            }

        }
    }
}