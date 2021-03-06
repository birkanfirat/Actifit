package com.example.actifit.helpers

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.actifit.data.remote.model.SongModel
import com.example.actifit.view.App
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SharedPreferenceHelper {

    val sharedPreferences: SharedPreferences?
        get() = if (App() != null) {
            PreferenceManager.getDefaultSharedPreferences(App.getContext())
        } else null

    val editor: SharedPreferences.Editor?
        get() = sharedPreferences?.edit()

    fun getString(key: String?): String {
        return sharedPreferences!!.getString(key, "")
    }

    fun put(key: String?, value: String?) {
        editor?.putString(key, value)?.apply()
    }

    fun setRecentlySongModel(addSongModel: SongModel) {
        val allSongModel: ArrayList<SongModel> = getRecentlySongModel()

        var resultFilter = allSongModel.filter {
            it == addSongModel
        }
        if (resultFilter.isEmpty()) {
            allSongModel.add(addSongModel)
        }

        val gson = Gson()
        val json = gson.toJson(allSongModel)
        put("RECENTLY_LIST", json)
    }

    private fun getRecentlySongModel(): ArrayList<SongModel> {
        val json: String = getString("RECENTLY_LIST")
        return if (json != null && json != "") {
            try {
                val gson = Gson()
                val type: Type =
                    object : TypeToken<List<SongModel?>?>() {}.type
                val songRecentList: List<SongModel> =
                    gson.fromJson<List<SongModel>>(json, type)
                songRecentList
            } catch (e: Exception) {
                arrayListOf<SongModel>()
            } as ArrayList<SongModel>
        } else arrayListOf<SongModel>()

    }

    fun searchRecentlySongModel(searchModel: SongModel): Boolean {
        val allSongModel: ArrayList<SongModel> = getRecentlySongModel()

        val resultFilter = allSongModel.filter {
            it == searchModel
        }
        return !resultFilter.isEmpty()
    }

    fun setDeleteSongModel(deleteSongModel: SongModel) {
        val deleteAllSongModel: ArrayList<SongModel> = getDeleteSongModel()

        var resultFilter = deleteAllSongModel.filter {
            it == deleteSongModel
        }
        if (resultFilter.isEmpty()) {
            deleteAllSongModel.add(deleteSongModel)
        }

        val gson = Gson()
        val json = gson.toJson(deleteAllSongModel)
        put("DELETE_LIST", json)
    }

    private fun getDeleteSongModel(): ArrayList<SongModel> {
        val json: String = getString("DELETE_LIST")
        return if (json != null && json != "") {
            try {
                val gson = Gson()
                val type: Type =
                    object : TypeToken<List<SongModel?>?>() {}.type
                val songDeleteList: List<SongModel> =
                    gson.fromJson<List<SongModel>>(json, type)
                songDeleteList
            } catch (e: Exception) {
                arrayListOf<SongModel>()
            } as ArrayList<SongModel>
        } else arrayListOf<SongModel>()

    }

    fun searchDeleteSongModel(searchModel: SongModel): Boolean {
        val deleteAllSongModel: ArrayList<SongModel> = getDeleteSongModel()

        val resultFilter = deleteAllSongModel.filter {
            it == searchModel
        }
        return !resultFilter.isEmpty()
    }
}