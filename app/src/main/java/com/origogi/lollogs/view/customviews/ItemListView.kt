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
import com.origogi.lollogs.model.ChampionSummary
import com.origogi.lollogs.setTextPercent

class ItemListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : ConstraintLayout(context, attrs, defStyleAttr) {


    init{
        LayoutInflater.from(context).inflate(R.layout.custom_item_list, this, true)
    }

    fun updateView(championSummary : List<ChampionSummary>) {

    }
}