package com.origogi.lollogs.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.origogi.lollogs.TAG
import com.origogi.lollogs.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {

    private val _summoner : MutableLiveData<Summoner> = MutableLiveData<Summoner>().apply {
        Summoner()
    }

    val summoner : LiveData<Summoner>
        get() = _summoner

    private val _recentGameSummary : MutableLiveData<RecentGameSummaryData> = MutableLiveData()
    val recentGameSummary : LiveData<RecentGameSummaryData>
        get() = _recentGameSummary


    fun searchData(name : String) {

        viewModelScope.launch {

            val summonerJob = async {
                RetrofitService.opggApi.getSummoner(name)
            }

            val matchesJob = async {
                RetrofitService.opggApi.getMatches(name)
            }

            val summonerResponse = summonerJob.await()
            var matchesResponse = matchesJob.await()

            _summoner.value = summonerResponse.summoner
            _recentGameSummary.value = makeRecentGameSummaryData(matchesResponse)
        }
    }

    private fun makeRecentGameSummaryData(matchesResponse: MatchesResponse) : RecentGameSummaryData {
        val sortedChampion = matchesResponse.champions.sortedByDescending {
            (it.wins * 100) / (it.losses + it.wins)
        }

        val mostPosition = matchesResponse.positions.maxByOrNull {
            (it.wins * 100) / (it.losses + it.wins)
        }!!

        return RecentGameSummaryData(sortedChampion, matchesResponse.summary, mostPosition)
    }
}