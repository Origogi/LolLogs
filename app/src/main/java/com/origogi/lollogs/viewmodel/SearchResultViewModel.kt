package com.origogi.lollogs.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.origogi.lollogs.TAG
import com.origogi.lollogs.model.RetrofitService
import com.origogi.lollogs.model.Summoner
import com.origogi.lollogs.model.SummonerResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {

    private val _summoner : MutableLiveData<SummonerResponse> = MutableLiveData<SummonerResponse>().apply {
        SummonerResponse()
    }


    val summoner : LiveData<SummonerResponse>
        get() = _summoner


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

            Log.d(TAG, matchesResponse.toString())

            _summoner.value = summonerResponse
        }
    }
}