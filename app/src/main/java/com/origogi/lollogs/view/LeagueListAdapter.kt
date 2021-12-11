package com.origogi.lollogs.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.origogi.lollogs.R
import com.origogi.lollogs.databinding.ListItemTierBinding
import com.origogi.lollogs.model.League

class LeagueListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var leagueItems: List<League> = emptyList()

    fun updateTierItem(newItems: List<League>) {
        leagueItems = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ListItemTierBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item_tier, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(leagueItems[position])
    }

    override fun getItemCount(): Int {
        return leagueItems.size
    }

    private class MyViewHolder(private val binding: ListItemTierBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(league: League) {
            binding.league = league
            binding.executePendingBindings()
        }
    }
}