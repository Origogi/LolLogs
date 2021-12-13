package com.origogi.lollogs.model

import com.origogi.lollogs.oneDay
import com.origogi.lollogs.oneHour
import com.origogi.lollogs.winsRate
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

data class SummonerResponse(
    var summoner: Summoner? = null
)

data class MatchesResponse(
    var champions: List<ChampionSummary> = emptyList(),
    var games: List<GameData> = emptyList(),
    var positions: List<Position> = emptyList(),
    var summary: Summary = Summary()

)

data class ChampionSummary(
    var id: Int = 0,
    var key: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var games: Int = 0,
    var kills: Int = 0,
    var deaths: Int = 0,
    var assists: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
) {
    val winsRateString: String
        get() = "${(wins to losses).winsRate}%"
}


data class Position(
    var games: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
    var position: String = "",
    var positionName: String = ""
) {
    val winsRateString: String
        get() = "${(wins to losses).winsRate}%"
}

data class Summary(
    var wins: Int = 0,
    var losses: Int = 0,
    var kills: Int = 0,
    var deaths: Int = 0,
    var assists: Int = 0,


    ) {
    val avgKillString: String
        get() = String.format("%.1f", kills / 20.0)
    val avgDeathString: String
        get() = String.format("%.1f", deaths / 20.0)
    val avgAssistString: String
        get() = String.format("%.1f", assists / 20.0)

    val kdaString: String
        get() = String.format("%.1f", (kills + assists) / Math.max(1, deaths).toDouble())

    val winAndLossString: String
        get() = "${wins}승 ${losses}패"

    val winsRateString: String
        get() = "${(wins to losses).winsRate}%"
}

sealed class ListType

data class RecentGameSummaryData(
    val mostChampions: List<ChampionSummary>,
    val summary: Summary,
    val position: Position
) : ListType()

data class GameData(
    var mmr: Int = 0,
    var champion: Champion,
    var spells: List<Spell> = emptyList(),
    var items: List<Item> = emptyList(),
    var needRenew: Boolean = false,
    var gameId: String = "",
    var createDate: Long = 0L,
    var gameLength: Long = 0L,
    var gameType: String = "",
    var summonerId: String = "",
    var summonerName: String = "",
    var tierRankShort: String = "",
    var stats: Stats = Stats(),
    var peak: List<String> = emptyList(),
    var isWin: Boolean = false

) : ListType() {

    val gameLengthToMMSSString: String
        get() = "${(gameLength / 60).toString().padStart(2, '0')}:${
            (gameLength % 60).toString().padStart(2, '0')
        }"

    val beforeGameTimeString: String
        get() {
            val createGameMills = createDate * 1000
            val currentMills = System.currentTimeMillis()
            val diffMills = currentMills - createGameMills

            return if (diffMills < oneDay) {
                if (diffMills < oneHour) {
                    val minutes = ((diffMills / 1000) / 60) % (60)
                    "${minutes}분 전"
                } else {
                    val hours = ((diffMills / 1000) / (60 * 60)) % (24)
                    "${hours}시간 전"
                }
            } else {
                val formatter = SimpleDateFormat("yyyy.MM.dd");
                formatter.format(Date(createGameMills))
            }

        }
}

data class Stats(
    var general: General = General(),
//    var ward : Ward = Ward(),
)

data class General(
    var kill: Int = 0,
    var death: Int = 0,
    var assists: Int = 0,
    var kdaString: String = "",
    var cs: Int = 0,
    var csPerMin: Double = 0.0,
    var contributionForKillRate: String = "",
    var goldEarned: Int = 0,
    var totalDamageDealtToChampions: Int = 0,
    var largestMultiKillString: String = "",
    var opScoreBadge: String = ""
)

data class Champion(
    var imageUrl: String = "",
    var level: Int = 0,
)

data class Item(
    var imageUrl: String = ""
)

data class Spell(
    var imageUrl: String = ""
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
) : ListType()

data class League(
    var hasResults: Boolean = false,
    var wins: Int = 0,
    var losses: Int = 0,
    var tierRank: Tier = Tier()
) {

    val winsRate: String
        get() {
            val rate = (wins * 100) / (wins + losses)
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

    val tierDivision: String
        get() {
            return "$tier ${shortString.last()}"
        }
}

data class LadderRank(
    var rank: Int = 0,
    var rankPercentOfTop: Int = 0
)
