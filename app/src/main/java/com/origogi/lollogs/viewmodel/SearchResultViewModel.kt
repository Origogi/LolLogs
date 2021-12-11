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
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {

    private val _summoner : MutableLiveData<SummonerResponse> = MutableLiveData<SummonerResponse>().apply {
        SummonerResponse()
    }


    val summoner : LiveData<SummonerResponse>
        get() = _summoner


    fun searchData(name : String) {

        viewModelScope.launch {
            val response = RetrofitService.opggApi.getSummoner(name)
            Log.d(TAG, response.toString())

            _summoner.value = response
        }
    }
}