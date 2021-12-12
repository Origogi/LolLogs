package com.origogi.lollogs.model

import java.text.DecimalFormat

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
    var games: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
    var position: String = "",
    var positionName: String = ""
)


data class Position(
    var games: Int = 0,
    var wins: Int = 0,
    var losses: Int = 0,
    var position: String = "",
    var positionName: String = ""
)

data class Summary(
    var wins: Int = 0,
    var losses: Int = 0,
    var killes: Int = 0,
    var deaths: Int = 0,
    var assists: Int = 0,
)

sealed class ListType

// TODO implement  MostChampions, GameData class
class RecentGameSummaryData : ListType()

data class GameData(
    var mmr: Int = 0,
    var champion: Champion,
    var spells: List<Spell>,
    var items : List<Item>,
    var needRenew : Boolean = false,
    var gameId : String = "",
    var createDate : Long = 0L,
    var gameLength : Long = 0L,
    var gameType: String = "",
    var summonerId : String = "",
    var summonerName : String = "",
    var tierRankShort : String = "",
    var stats : Stats = Stats(),
    var peak : List<String> = emptyList(),
    var isWin : Boolean = false

) : ListType()

data class Stats(
    var general : General = General(),
//    var ward : Ward = Ward(),
)

data class General(
    var kill : Int = 0,
    var death: Int = 0,
    var assists: Int = 0,
    var kdaString : String = "",
    var cs : Int = 0,
    var csPerMin : Double = 0.0,
    var contributionForKillRate : String = "",
    var goldEarned : Int = 0,
    var totalDamageDealtToChampions : Int = 0,
    var largestMultiKillString : String =  "",
    var opScoreBadge : String = ""
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
