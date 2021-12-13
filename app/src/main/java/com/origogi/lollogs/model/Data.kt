package com.origogi.lollogs.model

import com.origogi.lollogs.ONE_DAY
import com.origogi.lollogs.ONE_HOUR
import com.origogi.lollogs.winsRate
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

data class SummonerResponse(
    val summoner: Summoner = Summoner()
)

data class MatchesResponse(
    val champions: List<ChampionSummary> = emptyList(),
    val games: List<GameMatch> = emptyList(),
    val positions: List<Position> = emptyList(),
    val summary: Summary = Summary()
)

data class ChampionSummary(
    val id: Int = 0,
    val key: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val games: Int = 0,
    val kills: Int = 0,
    val deaths: Int = 0,
    val assists: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
) {
    val winsRateString: String
        get() = "${(wins to losses).winsRate}%"
}


data class Position(
    val games: Int = 0,
    val wins: Int = 0,
    val losses: Int = 0,
    val position: String = "",
    val positionName: String = ""
) {
    val winsRateString: String
        get() = "${(wins to losses).winsRate}%"
}

data class Summary(
    val wins: Int = 0,
    val losses: Int = 0,
    val kills: Int = 0,
    val deaths: Int = 0,
    val assists: Int = 0,
    ) {
    val avgKillString: String
        get() = String.format("%.1f", kills / 20.0)
    val avgDeathString: String
        get() = String.format("%.1f", deaths / 20.0)
    val avgAssistString: String
        get() = String.format("%.1f", assists / 20.0)

    val kdaString: String
        get() = String.format("%.1f", (kills + assists) / max(1, deaths).toDouble())

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

data class GameMatch(
    val mmr: Int = 0,
    val champion: Champion = Champion(),
    val spells: List<Spell> = emptyList(),
    val items: List<Item> = emptyList(),
    val needRenew: Boolean = false,
    val gameId: String = "",
    val createDate: Long = 0L,
    val gameLength: Long = 0L,
    val gameType: String = "",
    val summonerId: String = "",
    val summonerName: String = "",
    val tierRankShort: String = "",
    val stats: Stats = Stats(),
    val peak: List<String> = emptyList(),
    val isWin: Boolean = false

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

            return if (diffMills < ONE_DAY) {
                if (diffMills < ONE_HOUR) {
                    val minutes = ((diffMills / 1000) / 60) % (60)
                    "${minutes}분 전"
                } else {
                    val hours = ((diffMills / 1000) / (60 * 60)) % (24)
                    "${hours}시간 전"
                }
            } else {
                val formatter = SimpleDateFormat("yyyy.MM.dd")
                formatter.format(Date(createGameMills))
            }
        }
}

data class Summoner(
    val name: String = "",
    val level: Int = 0,
    val profileImageUrl: String = "",
    val profileBorderImageUrl: String = "",
    val url: String = "",
    val leagues: List<League> = emptyList(),
    val ladderRank: LadderRank = LadderRank(),
    val profileBackgroundImageUrl: String = ""
) : ListType()


data class Stats(
    val general: General = General(),
//    var ward : Ward = Ward(),
)

data class General(
    val kill: Int = 0,
    val death: Int = 0,
    val assists: Int = 0,
    val kdaString: String = "",
    val cs: Int = 0,
    val csPerMin: Double = 0.0,
    val contributionForKillRate: String = "",
    val goldEarned: Int = 0,
    val totalDamageDealtToChampions: Int = 0,
    val largestMultiKillString: String = "",
    val opScoreBadge: String = ""
)

data class Champion(
    val imageUrl: String = "",
    val level: Int = 0,
)

data class Item(
    val imageUrl: String = ""
)

data class Spell(
    val imageUrl: String = ""
)


data class League(
    val hasResults: Boolean = false,
    val wins: Int = 0,
    val losses: Int = 0,
    val tierRank: Tier = Tier()
) {

    val winsRate: String
        get() {
            val rate = (wins * 100) / (wins + losses)
            return "${wins}승 ${losses}패 ($rate%)"
        }
}

data class Tier(
    val name: String = "",
    val tier: String = "",
    val string: String = "",
    val shortString: String = "",
    val division: String = "",
    val imageUrl: String = "",
    val lp: Int = 0,
    val tierRankPoint: Int = 0,
    val season: Int = 0

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
    val rank: Int = 0,
    val rankPercentOfTop: Int = 0
)
