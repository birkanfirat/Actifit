package com.example.actifit.view.song

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.actifit.R
import com.example.actifit.base.BaseActivity
import com.example.actifit.data.local.songItem
import com.example.actifit.data.remote.model.SongModel
import com.example.actifit.helpers.Router
import com.example.actifit.helpers.enums.SearchTypeEnum
import com.example.actifit.helpers.listener.SelectListener
import com.example.actifit.view.song.adapter.SongsAdapter
import com.example.actifit.view.songDetail.SongDetailFragment
import kotlinx.android.synthetic.main.song_activity.*

class SongActivity : BaseActivity(), SelectListener<SongModel> {

    lateinit var songAdapter: SongsAdapter
    lateinit var layoutManager: GridLayoutManager
    private lateinit var viewModel: SongsActivityViewModel
    var searchType: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_activity)

        viewModel = ViewModelProviders.of(
            this, SongActivityViewModelFactory()
        ).get(SongsActivityViewModel::class.java)

        viewModel.resultSearch.observe(this, Observer {

            val resultSearch = it ?: return@Observer

            if (resultSearch != null || resultSearch.results.isNullOrEmpty()) {
                songAdapter.songList.clear()
                songAdapter.notifyDataSetChanged()
            }
            if (!resultSearch.results.isNullOrEmpty()) {
                songAdapter.songList.clear()
                songAdapter.songList.addAll(resultSearch.results)
                songAdapter.notifyDataSetChanged()
            }

        })

        edtxtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.callSong(s.toString(), searchType.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        imgSelectType.setOnClickListener {
            showSelectType()
        }
        imgBack.setOnClickListener {
            onBackPressed()
        }
        imgDeleteIcon.setOnClickListener {

        }

        init()
    }

    override fun onItemClicked(item: SongModel) {
        songItem = item
        hideSoftKeyboard(edtxtSearch)
        Router().addFragment(this, SongDetailFragment())
    }

    fun init() {
        layoutManager = GridLayoutManager(this, 1)
        songAdapter =
            SongsAdapter(
                this,
                arrayListOf(),
                this
            )
        rcylrAllSongResult.adapter = songAdapter
        rcylrAllSongResult.layoutManager = layoutManager
    }

    private fun showSelectType() {
        val arrayType = arrayOf(
            SearchTypeEnum.musicVideo.value,
            SearchTypeEnum.podcast.value,
            SearchTypeEnum.movie.value,
            SearchTypeEnum.all.value
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.selectCategory))
        builder.setItems(arrayType) { _, which ->
            try {

                when (which) {
                    0 -> {
                        searchType = SearchTypeEnum.musicVideo.toString()
                        tvTitle.text = SearchTypeEnum.musicVideo.value
                    }

                    1 -> {
                        searchType = SearchTypeEnum.podcast.toString()
                        tvTitle.text = SearchTypeEnum.podcast.value
                    }

                    2 -> {
                        searchType = SearchTypeEnum.movie.toString()
                        tvTitle.text = SearchTypeEnum.movie.value
                    }

                    3 -> {
                        searchType = ""
                        tvTitle.text = getString(R.string.appName)
                    }
                }
                if (!edtxtSearch.text.isNullOrEmpty()) {
                    viewModel.callSong(edtxtSearch.text.toString(), searchType.toString())
                }

            } catch (e: IllegalArgumentException) {

            }
        }
        val dialog = builder.create()
        dialog.show()
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
