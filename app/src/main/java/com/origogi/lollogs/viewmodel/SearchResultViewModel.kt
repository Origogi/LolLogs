package com.origogi.lollogs.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.origogi.lollogs.TAG
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

    private val _showLoadingInd: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        false
    }
    val showLoadingInd: LiveData<Boolean>
        get() = _showLoadingInd

    private var summonerName = ""


    fun searchData(name: String) {

        viewModelScope.launch {
            summonerName = name
            _showLoadingInd.value = true

            val summonerJob = async {
                RetrofitService.opggApi.getSummoner(summonerName)
            }

            val matchesJob = async {
                RetrofitService.opggApi.getMatches(summonerName)
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

    fun loadMore() {
        viewModelScope.launch {
            val currentList = listItems.value ?: emptyList()

            if (currentList.isEmpty() || currentList.last() !is GameData) {
                Log.e(TAG, "Match data is not exist")
            } else {
                val lastGameCreateDate = (currentList.last() as GameData).createDate
                val matchesResponse = RetrofitService.opggApi.loadMoreMatches(summonerName, lastGameCreateDate)
                _listItems.value = currentList + matchesResponse.games
            }
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