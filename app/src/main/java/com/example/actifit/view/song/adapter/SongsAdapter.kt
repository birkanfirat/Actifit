package com.example.actifit.view.song.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actifit.R
import com.example.actifit.data.remote.model.SongModel
import com.example.actifit.helpers.Others
import com.example.actifit.helpers.SharedPreferenceHelper
import com.example.actifit.helpers.listener.SelectListener
import kotlinx.android.synthetic.main.row_song.view.*

class SongsAdapter(
    val context: Context,
    var songList: ArrayList<SongModel>,
    var listener: SelectListener<SongModel>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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
        if (itemRow != null) {

            //Eğer daha önceden ziyaret edildi ise renk ve türünü değiştirdik.
            if (SharedPreferenceHelper().searchRecentlySongModel(itemRow)) {
                holder.itemView.tvSongName.setTextColor(ContextCompat.getColor(context, R.color.colorYellow))
                holder.itemView.tvSongName.setTypeface(null, Typeface.BOLD_ITALIC)

            }
            else{
                holder.itemView.tvSongName.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.itemView.tvSongName.setTypeface(null, Typeface.NORMAL)
            }

            Glide.with(context).load(itemRow.artworkUrl100).centerCrop()
                .placeholder(Others().getCircularProgressDrawable(context))
                .into(holder.itemView.imgSong)

            holder.itemView.tvSongName.text = itemRow.trackName

            holder.itemView.setOnClickListener {
                listener.onItemClicked(itemRow)
                notifyDataSetChanged()
            }
        }


    }

    class songsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}