package com.origogi.lollogs.model

data class SummonerResponse(
    var summoner: Summoner
)

data class Summoner(
    var name: String = "",
    var level: Int = 0,
    var profileImageUrl: String = "",
    var profileBorderImageUrl: String = "",
    var url: String = "",
    var leagues: List<League> = emptyList(),
    var ladderRank : LadderRank = LadderRank(),
    var profileBackgroundImageUrl : String = ""
)

data class League(
    var hasResults: Boolean = false,
    var wins: Int = 0,
    var losses: Int = 0,
    var tierRank: Tier = Tier()
)

data class Tier(
    var name: String = "",
    var tier: String = "",
    var tierDivision: String = "",
    var string: String = "",
    var shortString: String = "",
    var division: String = "",
    var imageUrl: String = "",
    var lp: Int = 0,
    var tierRankPoint: Int = 0,
    var season: Int = 0
)

data class LadderRank(
    var rank: Int = 0,
    var rankPercentOfTop: Int = 0
)
