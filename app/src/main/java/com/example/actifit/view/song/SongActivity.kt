package com.example.actifit.view.song

import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actifit.R
import com.example.actifit.base.BaseActivity
import com.example.actifit.data.remote.ActifitApi
import com.example.actifit.data.remote.ApiClient
import com.example.actifit.view.song.adapter.SongsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class SongActivity : BaseActivity() {

    lateinit var songAdapter: SongsAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var apiInterface: ActifitApi
    private lateinit var viewModel: SongsActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(
            this, SongActivityViewModelFactory()
        ).get(SongsActivityViewModel::class.java)


        viewModel.resultSearch.observe(this, Observer {

            val resultSearch = it ?: return@Observer

            if (resultSearch != null && resultSearch.results.isNullOrEmpty()) {

            }
            if (!resultSearch.results.isNullOrEmpty()) {
                songAdapter.songList.addAll(resultSearch.results)
                songAdapter.notifyDataSetChanged()
            }

        })

        init()
    }

    fun init() {
        apiInterface = ApiClient.getClient().create(ActifitApi::class.java)
        layoutManager = GridLayoutManager(this, 1)
        songAdapter =
            SongsAdapter(
                this,
                arrayListOf()
            )
        rcylrAllSongResult.adapter = songAdapter
        rcylrAllSongResult.layoutManager = layoutManager


        viewModel.callSong("John")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(this, 2)
            rcylrAllSongResult.layoutManager = layoutManager


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = GridLayoutManager(this, 1)
            rcylrAllSongResult.layoutManager = layoutManager


        }
    }


}
