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

class MostChampionsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val images : List<ImageView>
    private val texts : List<TextView>

    init{
        LayoutInflater.from(context).inflate(R.layout.custom_most_champions, this, true)
        images = listOf(findViewById(R.id.mostChampImage0), findViewById(R.id.mostChampImage1))
        texts = listOf(findViewById(R.id.mostChampWinRate0) , findViewById(R.id.mostChampWinRate1))
    }

    fun updateView(championSummary : List<ChampionSummary>) {
        images.forEach {
            it.visibility = View.GONE
        }
        texts.forEach {
            it.visibility = View.GONE
        }
        championSummary.forEachIndexed { i , data ->
            if (i == 2) {
                return@forEachIndexed
            }

            loadImageCircleCrop(images[i], data.imageUrl)
            setTextPercent(texts[i], data.winsRateString)

            images[i].visibility = View.VISIBLE
            texts[i].visibility = View.VISIBLE
        }
    }
}