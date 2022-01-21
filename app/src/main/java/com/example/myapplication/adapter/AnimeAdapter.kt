package com.example.myapplication.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Anime

class AnimeAdapter(private val context: Context, private val animeList: ArrayList<Anime>, private val listener: OnItemClickListener):
    RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val animeName: TextView = view.findViewById(R.id.animeName)
        val animeImage: ImageView = view.findViewById(R.id.animeImage)

        private val item: CardView = view.findViewById(R.id.listItem)

        init {
            item.setOnClickListener(this)

            item.findViewById<ImageView>(R.id.save).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemSave(position)
                }
            }

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAnime = animeList[position]

        holder.animeName.text = currentAnime.animeName

        Glide.with(context)
            .load(currentAnime.animeImageUrl)
            .into(holder.animeImage)

    }

    override fun getItemCount() = animeList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemSave(position: Int)
    }

}