package com.origogi.lollogs

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.origogi.lollogs.model.*
import com.origogi.lollogs.view.LeagueListAdapter
import com.origogi.lollogs.view.SearchResultListAdapter
import com.origogi.lollogs.view.customviews.MostChampionsView

@BindingAdapter("loadImageCircleCrop")
fun loadImageCircleCrop(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("loadPositionImage")
fun loadPositionImage(imageView: ImageView, position: String) {
    Glide.with(imageView.context)
        .load(positionImageMap[position])
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("bindLeague")
fun bindLeague(recyclerView: RecyclerView, items: List<League>) {
    val adapter = recyclerView.adapter
    if (adapter is LeagueListAdapter) {
        adapter.updateTierItem(items)
    }
}

@BindingAdapter("bindItem")
fun bindSummoner(recyclerView: RecyclerView, items: LiveData<List<ListType>>) {
    val adapter = recyclerView.adapter
    if (adapter is SearchResultListAdapter) {
        items.value?.let {
            adapter.update(it)
        }
    }
}


@BindingAdapter("bindMostChampions")
fun bindMostChampions(mostChampionsView: MostChampionsView, mostChampions : List<ChampionSummary>) {
    mostChampionsView.updateView(mostChampions)
}

@BindingAdapter("setTextPercent")
fun setTextPercent(textView: TextView, value : String) {
    if (value.startsWith("100")) {
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.darkish_pink))
    }
    textView.text = value
}






