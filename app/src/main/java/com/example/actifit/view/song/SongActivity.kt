package com.example.actifit.view.song

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.actifit.R
import com.example.actifit.base.BaseActivity
import com.example.actifit.data.local.songItem
import com.example.actifit.data.remote.model.SongModel
import com.example.actifit.helpers.Router
import com.example.actifit.helpers.SharedPreferenceHelper
import com.example.actifit.helpers.enums.SearchTypeEnum
import com.example.actifit.helpers.listener.SelectListener
import com.example.actifit.view.song.adapter.SongsAdapter
import com.example.actifit.view.songDetail.SongDetailFragment
import kotlinx.android.synthetic.main.song_activity.*

class SongActivity : BaseActivity(), SelectListener<SongModel> {

    var lastClickTime = System.currentTimeMillis()
    var CLICK_TIME_INTERVAL = 300

    lateinit var songAdapter: SongsAdapter
    lateinit var layoutManager: GridLayoutManager
    private lateinit var viewModel: SongsActivityViewModel
    var searchType: String? = ""
    //Default tür tümü seçili olması isteniyor.
    var selectedType: Int = 3
    var selectedTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_activity)

        viewModel = ViewModelProviders.of(
            this, SongActivityViewModelFactory()
        ).get(SongsActivityViewModel::class.java)

        //Response observe
        viewModel.resultSearch.observe(this, Observer {

            val resultSearch = it ?: return@Observer

            if (resultSearch != null || resultSearch.results.isNullOrEmpty()) {
                // Adapteri temizledik Boş yazısı gösterildi.
                songAdapter.songList.clear()
                songAdapter.notifyDataSetChanged()
                rcylrAllResult.visibility = View.GONE
                txtNoResult.visibility = View.VISIBLE
            }
            if (!resultSearch.results.isNullOrEmpty()) {

                rcylrAllResult.visibility = View.VISIBLE
                txtNoResult.visibility = View.GONE

                var resultSongModel = arrayListOf<SongModel>()

                resultSearch.results.forEach {
                    if (!SharedPreferenceHelper().searchDeleteSongModel(it)) {
                        resultSongModel.add(it)
                    } else
                        return@forEach
                }
                songAdapter.songList.clear()
                songAdapter.songList.addAll(resultSongModel)
                songAdapter.notifyDataSetChanged()
            }

        })

        // Error observe
        viewModel.resultErrorSearch.observe(this, Observer {

            val resultSearch = it ?: return@Observer

            if (!resultSearch.isNullOrEmpty()) {
                Toast.makeText(this, resultSearch.toString(), Toast.LENGTH_LONG).show()
                songAdapter.songList.clear()
                songAdapter.notifyDataSetChanged()
                rcylrAllResult.visibility = View.GONE
                txtNoResult.visibility = View.VISIBLE
            }

        })

        //Edittext Dinleniyor Service call
        edtxtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Performans için klavyedeki tıklanma olayını handle ediyoruz.
                val now = System.currentTimeMillis()
                if (now - lastClickTime < CLICK_TIME_INTERVAL) {
                    return
                }
                lastClickTime = now

                viewModel.callSong(s.toString(), searchType.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        // Tür Seçimi
        imgSelectType.setOnClickListener {
            showSelectType()
        }
        //Back Button Tıklandığında
        imgBack.setOnClickListener {
            onBackPressed()
        }
        // Detayda delete iconu
        imgDeleteIcon.setOnClickListener {
            checkedChoose()
        }

        init()
    }

    //Adapterda item listener'ı dinliyoruz.
    override fun onItemClicked(item: SongModel) {
        songItem = item
        hideSoftKeyboard(edtxtSearch)
        Router().addFragment(this, SongDetailFragment())
        SharedPreferenceHelper().setRecentlySongModel(item)
    }

    fun init() {
        //RecylerView ve adapter initialize edildi.
        layoutManager = GridLayoutManager(this, 1)
        songAdapter =
            SongsAdapter(
                this,
                arrayListOf(),
                this
            )
        rcylrAllResult.adapter = songAdapter
        rcylrAllResult.layoutManager = layoutManager
    }

    // Tür Seçimi
    private fun showSelectType() {
        val arrayType = arrayOf(
            SearchTypeEnum.musicVideo.value,
            SearchTypeEnum.podcast.value,
            SearchTypeEnum.movie.value,
            SearchTypeEnum.all.value
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.selectCategory))
        builder.setSingleChoiceItems(arrayType, selectedType) { dialog, which ->
            try {
                when (which) {
                    0 -> {
                        searchType = SearchTypeEnum.musicVideo.toString()
                        selectedTitle = SearchTypeEnum.musicVideo.value
                    }

                    1 -> {
                        searchType = SearchTypeEnum.podcast.toString()
                        selectedTitle = SearchTypeEnum.podcast.value
                    }

                    2 -> {
                        searchType = SearchTypeEnum.movie.toString()
                        selectedTitle = SearchTypeEnum.movie.value
                    }

                    3 -> {
                        searchType = ""
                        selectedTitle = getString(R.string.appName)
                    }
                }

                selectedType = which

            } catch (e: IllegalArgumentException) {

            }
        }
        builder.setPositiveButton(
            getString(R.string.ok)
        ) { dialog, which ->

            if (!edtxtSearch.text.isNullOrEmpty()) {
                viewModel.callSong(edtxtSearch.text.toString(), searchType.toString())
            }
            tvTitle.text = selectedTitle
        }

        val dialog = builder.create()
        dialog.show()
    }

    //Delete Dialogu
    private fun checkedChoose() {
        val builder = AlertDialog.Builder(this)

        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        deleteItem()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }

        builder.setMessage(getString(R.string.areYouSure))
            .setPositiveButton(getString(R.string.yes), dialogClickListener)
            .setNegativeButton(getString(R.string.no), dialogClickListener).show()
    }

    //Shared Pref'e silinen itemı kaydediyoruz.
    fun deleteItem() {
        songItem?.let { SharedPreferenceHelper().setDeleteSongModel(it) }
        if (!edtxtSearch.text.isNullOrEmpty()) {
            viewModel.callSong(edtxtSearch.text.toString(), searchType.toString())
        }
        onBackPressed()

    }

    //Telefon yan çevirildiğinde tetikleniyor.
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //LANDSCAPE durumunda listede 2 item görünmesi isteniyor.
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(this, 2)
            rcylrAllResult.layoutManager = layoutManager

        }
        //PORTRAIT durumunda listede 1 item görünmesi isteniyor.
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = GridLayoutManager(this, 1)
            rcylrAllResult.layoutManager = layoutManager
        }
    }


}
