package com.origogi.lollogs.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.origogi.lollogs.TAG
import com.origogi.lollogs.model.RetrofitService
import kotlinx.coroutines.launch

class SearchResultViewModel : ViewModel() {
    fun searchData(name : String) {

        viewModelScope.launch {
            val response = RetrofitService.opggApi.getSummoner(name)
            Log.d(TAG, response.toString())
        }
    }
}