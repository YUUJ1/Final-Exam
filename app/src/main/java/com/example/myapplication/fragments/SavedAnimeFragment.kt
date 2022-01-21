package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.disklrucache.DiskLruCache
import com.example.myapplication.R
import com.example.myapplication.adapter.AnimeAdapter
import com.example.myapplication.model.Anime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SavedAnimeFragment : Fragment(R.layout.fragment_savedanime), AnimeAdapter.OnItemClickListener {

    private lateinit var recyclerview: RecyclerView
    private lateinit var arrayListAnimes: ArrayList<Anime>
    private val db = FirebaseDatabase.getInstance().getReference("users")
    private val dbAnimes = FirebaseDatabase.getInstance().getReference("animes")
    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        loadSavedAnimes()
    }

    private fun init(view: View) {
        recyclerview = view.findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun loadSavedAnimes() {
        db.child(auth.currentUser!!.uid).child("savedAnimes").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {

                        val currentItem = snap.value.toString()

                        dbAnimes.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val currentAnime = snapshot.child(currentItem).getValue(Anime::class.java)?: return
                                arrayListAnimes.add(currentAnime)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                    recyclerview.adapter = AnimeAdapter(context!!.applicationContext, arrayListAnimes, this@SavedAnimeFragment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onItemClick(position: Int) {
        val currentItem = arrayListAnimes[position]

        val intent = Intent(activity, WatchActivity::class.java)
        intent.putExtra("currentAnimeName", currentItem.animeName)
        intent.putExtra("currentAnimeImageUrl", currentItem.animeImageUrl)
        intent.putExtra("currentAnimeId", currentItem.animeId)
        startActivity(intent)
    }

    override fun onItemSave(position: Int) {
        val currentItem = arrayListAnimes[position]

        val intent = Intent(activity, WatchActivity::class.java)
        intent.putExtra("currentAnimeName", currentItem.animeName)
        intent.putExtra("currentAnimeImageUrl", currentItem.animeImageUrl)
        intent.putExtra("currentAnimeId", currentItem.animeId)
        startActivity(intent)
    }

}