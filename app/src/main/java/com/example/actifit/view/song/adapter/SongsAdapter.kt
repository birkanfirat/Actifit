package com.example.actifit.view.song.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actifit.R
import com.example.actifit.data.remote.model.SongModel
import kotlinx.android.synthetic.main.row_song.view.*

class SongsAdapter (val context: Context, var songList: ArrayList<SongModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(context).inflate(R.layout.row_song, p0, false)

        return songsAdapterHolder(
            v
        )
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val itemRow = songList[position]
        if(itemRow!=null){

            Glide.with(context).load(itemRow.artworkUrl100).centerCrop()
                .into(holder.itemView.imgSong)
            holder.itemView.tvSongName.text = itemRow.trackName
          //  holder.itemView.tvSongDetail.text = itemRow.artistName


        }




    }
    class songsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}