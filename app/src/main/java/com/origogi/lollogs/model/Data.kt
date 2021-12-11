package com.origogi.lollogs.model

import java.text.DecimalFormat

data class SummonerResponse(
    var summoner: Summoner? = null
)

data class Summoner(
    var name: String = "",
    var level: Int = 0,
    var profileImageUrl: String = "",
    var profileBorderImageUrl: String = "",
    var url: String = "",
    var leagues: List<League> = emptyList(),
    var ladderRank: LadderRank = LadderRank(),
    var profileBackgroundImageUrl: String = ""
)

data class League(
    var hasResults: Boolean = false,
    var wins: Int = 0,
    var losses: Int = 0,
    var tierRank: Tier = Tier()
) {

    val winsRate: String
        get() {
            val rate = (wins * 100) /  (wins + losses)
            return "${wins}승 ${losses}패 ($rate%)"
        }
}

data class Tier(
    var name: String = "",
    var tier: String = "",
    var string: String = "",
    var shortString: String = "",
    var division: String = "",
    var imageUrl: String = "",
    var lp: Int = 0,
    var tierRankPoint: Int = 0,
    var season: Int = 0

) {
    val lpStrFormat: String
        get() {
            val dec = DecimalFormat("#,###")
            val lpDecimal = dec.format(lp)
            return "$lpDecimal LP"
        }

    val tierDivision : String
        get() {
            return "$tier ${shortString.last()}"
        }
}

data class LadderRank(
    var rank: Int = 0,
    var rankPercentOfTop: Int = 0
)
