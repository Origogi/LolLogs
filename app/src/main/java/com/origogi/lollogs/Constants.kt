package com.origogi.lollogs

const val TAG = "KJT"

val positionImageMap: Map<String, Int> = mapOf(
    "ADC" to R.drawable.ic_icon_lol_bot,
    "JNG" to R.drawable.ic_icon_lol_jng,
    "MID" to R.drawable.ic_icon_lol_mid,
    "TOP" to R.drawable.ic_icon_lol_top,
    "SUP" to R.drawable.ic_icon_lol_sup,
)

val opBadgeBackground: Map<String, Int> = mapOf(
    "ACE" to R.drawable.rect_periwinkle,
    "MVP" to R.drawable.rect_yellow
)

var dpToPixel4: Int = 0

const val ONE_DAY = 24 * 60 * 60 * 1000
const val ONE_HOUR = 60 * 60 * 1000
