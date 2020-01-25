package com.example.actifit.view.songDetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.actifit.R
import com.example.actifit.data.local.songItem
import kotlinx.android.synthetic.main.row_song.view.*
import kotlinx.android.synthetic.main.song_detail_fragment.*

class SongDetailFragment : Fragment() {

    companion object {
        fun newInstance() = SongDetailFragment()
    }

    private lateinit var viewModel: SongDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.song_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SongDetailViewModel::class.java)

        init()

    }

    fun init() {
        if (songItem != null) {
            var songItem = songItem!!
            var pictureUrl = songItem.artworkUrl100.replace("100x100bb.jpg","576x320.jpg")
            Glide.with(this).load(pictureUrl).centerCrop()
                .into(imgSong)
            txtSongName.text = songItem.trackName
            artistName.text = songItem.artistName
            collectionName.text = songItem.collectionName
            genreName.text = songItem.primaryGenreName
            releaseDate.text = songItem.releaseDate
            country.text = songItem.country
            price.text = songItem.trackPrice.toString() + " " + songItem.currency
            video.text = songItem.trackViewUrl
        }
        video.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.text.toString()))
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
            }
        }
    }

}
