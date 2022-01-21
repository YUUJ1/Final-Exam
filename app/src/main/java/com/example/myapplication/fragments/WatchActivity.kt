package com.example.myapplication.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class WatchActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_watch)

        imageView = findViewById(R.id.imageView)
        title = findViewById(R.id.title)


        val name = intent.getStringExtra("currentAnimeName")
        val id = intent.getStringExtra("currentAnimeId")
        val imageUrl = intent.getStringExtra("currentAnimeImageUrl")

        title.text = name

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

    }
}