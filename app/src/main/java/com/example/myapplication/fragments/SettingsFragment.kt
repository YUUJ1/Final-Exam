package com.example.myapplication.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var editTextNewUsername: EditText
    private lateinit var buttonLogout: Button
    private lateinit var buttonChangeUsername: Button
    private lateinit var userName: TextView


    private val db = FirebaseDatabase.getInstance().getReference("users")
    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        buttonLogout = view.findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        userName = view.findViewById(R.id.userName)
        db.child(auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser = snapshot.child("userName").value.toString()
                userName.text = currentUser
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        editTextNewUsername = view.findViewById(R.id.editTextNewUsername)
        buttonChangeUsername = view.findViewById(R.id.buttonChangeUsername)
        buttonChangeUsername.setOnClickListener {
            if (editTextNewUsername.text.isNotEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Username Change")
                    .setMessage("Are you sure that you want to change your username?")
                    .setPositiveButton("Yes") { dialog, _ ->
                        val newUsername = editTextNewUsername.text.toString()
                        db.child(auth.currentUser!!.uid).child("userName").setValue(newUsername)
                        dialog.dismiss()

                    }.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }

    }

}