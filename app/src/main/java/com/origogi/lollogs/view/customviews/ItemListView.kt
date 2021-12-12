package com.origogi.lollogs.view.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.origogi.lollogs.R
import com.origogi.lollogs.loadImageCircleCrop
import com.origogi.lollogs.loadImageRoundCorner
import com.origogi.lollogs.model.ChampionSummary
import com.origogi.lollogs.model.Item
import com.origogi.lollogs.setTextPercent

class ItemListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val itemImages: List<ImageView>
    private val trinketImage: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_item_list, this, true)
        itemImages = listOf(
            findViewById(R.id.item0),
            findViewById(R.id.item1),
            findViewById(R.id.item2),
            findViewById(R.id.item3),
            findViewById(R.id.item4),
            findViewById(R.id.item5),
        )
        trinketImage = findViewById(R.id.trinket)
    }

    fun update(items: List<Item>) {
        if (items.isEmpty()) {
            return
        }

        if (items.size > 1) {
            (0..items.size - 2).forEach {
                loadImageRoundCorner(itemImages[it], items[it].imageUrl)
            }
        }

        loadImageCircleCrop(trinketImage, items.last().imageUrl)
    }
}