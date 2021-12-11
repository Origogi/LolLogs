package com.origogi.lollogs

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.origogi.lollogs.model.League
import com.origogi.lollogs.model.SummonerResponse
import com.origogi.lollogs.view.LeagueListAdapter
import com.origogi.lollogs.view.SearchResultListAdapter

@BindingAdapter("loadProfileImage")
fun loadProfileImage(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("loadTierImage")
fun loadTierImage(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("bindItem")
fun bindItem(recyclerView: RecyclerView, items: List<League>) {
    val adapter = recyclerView.adapter
    if (adapter is LeagueListAdapter) {
        adapter.updateTierItem(items)
    }
}

@BindingAdapter("bindSummoner")
fun bindSummoner(recyclerView: RecyclerView, summonerResponse: LiveData<SummonerResponse>) {
    val adapter = recyclerView.adapter
    if (adapter is SearchResultListAdapter) {
        summonerResponse.value?.summoner?.let {
            adapter.updateSummoner(it)
        }
    }
}




