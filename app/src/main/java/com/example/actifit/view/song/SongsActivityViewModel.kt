package com.example.actifit.view.song

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.actifit.data.remote.ActifitApi
import com.example.actifit.data.remote.ApiClient
import com.example.actifit.data.remote.model.ResultModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongsActivityViewModel(activity: Activity) : ViewModel() {
    var activity = activity
    var _resultSearch = MutableLiveData<ResultModel>()
    var resultSearch: LiveData<ResultModel> = _resultSearch
    var _resultErrorSearch = MutableLiveData<String>()
    var resultErrorSearch: LiveData<String> = _resultErrorSearch

    fun callSong(searchText: String, searchType: String) {

        var apiInterface = ApiClient.getClient().create(ActifitApi::class.java)

        val getSong = apiInterface.getAll(searchText, searchType, 100)
        getSong.request()
        getSong.enqueue(object : Callback<ResultModel> {

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
               _resultErrorSearch.value =t.message.toString()

            }

            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {

                if (response != null) {

                    if (response.isSuccessful) {
                        _resultSearch.value = response.body()
                    } else {
                        _resultErrorSearch.value =response.message()
                    }


                }
            }
        })
    }
}