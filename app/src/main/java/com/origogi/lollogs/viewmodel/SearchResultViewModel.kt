package com.origogi.lollogs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.origogi.lollogs.model.*
import com.origogi.lollogs.winsRate
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {

    private val _listItems: MutableLiveData<List<ListType>> =
        MutableLiveData<List<ListType>>().apply {
            emptyList<ListType>()
        }
    val listItems: LiveData<List<ListType>>
        get() = _listItems

    private val _showLoadingInd : MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        false
    }
    val showLoadingInd : LiveData<Boolean>
        get() = _showLoadingInd


    fun searchData(name: String) {

        viewModelScope.launch {

            _showLoadingInd.value = true

            val summonerJob = async {
                RetrofitService.opggApi.getSummoner(name)
            }

            val matchesJob = async {
                RetrofitService.opggApi.getMatches(name)
            }

            val summonerResponse = summonerJob.await()
            val matchesResponse = matchesJob.await()

            _listItems.value = mutableListOf<ListType>().apply {
                summonerResponse.summoner?.let { add(it) }
                add(makeRecentGameSummaryData(matchesResponse))
                addAll(matchesResponse.games)
            }

            _showLoadingInd.value = false
        }
    }

    private fun makeRecentGameSummaryData(matchesResponse: MatchesResponse): RecentGameSummaryData {
        val sortedChampion = matchesResponse.champions.sortedByDescending {
            (it.wins to it.losses).winsRate
        }

        val mostPosition = matchesResponse.positions.maxByOrNull {
            (it.wins to it.losses).winsRate
        }!!

        return RecentGameSummaryData(sortedChampion, matchesResponse.summary, mostPosition)
    }
}