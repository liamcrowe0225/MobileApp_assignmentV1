package com.example.mobileapp_assignmentv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import com.example.mobileapp_assignmentv1.R
import com.google.android.material.snackbar.Snackbar
import com.example.mobileapp_assignmentv1.models.Fixtures
import com.example.mobileapp_assignmentv1.databinding.CreatingFixtureBinding
import com.example.mobileapp_assignmentv1.main.Main


    class CreateFixtures : AppCompatActivity() {

        private lateinit var binding: CreatingFixtureBinding
        var dataClassClubNames = Fixtures()
        lateinit var app: Main

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = CreatingFixtureBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.toolbarAdd.title = title
            setSupportActionBar(binding.toolbarAdd)
            //  binding.btnDelete.isVisible = false
            //R.id.delete.isVisible = false
            //R.id.update.isVisible = false
            app = application as Main

            if (intent.hasExtra("Edit")) {
                dataClassClubNames = intent.extras?.getParcelable("Edit")!!
                binding.team.setText(dataClassClubNames.team1)
                // binding.btnDelete.isVisible = true
                //R.id.delete.isVisible = true
                //R.id.update.isVisible = true
            }

            binding.btnAdd.setOnClickListener() {
                dataClassClubNames.team1 = binding.team.text.toString()
                dataClassClubNames.team2 = binding.team2.text.toString()
                dataClassClubNames.score1 = (binding.score1.text.toString()).toInt()
                dataClassClubNames.score2 = (binding.score2.text.toString()).toInt()
                dataClassClubNames.venue = binding.venue.text.toString()
                dataClassClubNames.date = binding.date.text.toString()
                if (dataClassClubNames.team1.isNotEmpty()) {
                    app.fixtures.create(dataClassClubNames.copy())
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Snackbar
                        .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                        .show()
                }
            }

/*
        binding.btnDelete.setOnClickListener() {
            app.placemarks.delete(dataClassClubNames.copy())
            setResult(RESULT_FIRST_USER)
            finish()
        }



        binding.btnUpdate.setOnClickListener() {
            dataClassClubNames.team1 = binding.team.text.toString()
            dataClassClubNames.team2 = binding.team2.text.toString()
            dataClassClubNames.score1 = (binding.score1.text.toString()).toInt()
            dataClassClubNames.score2 = (binding.score2.text.toString()).toInt()
            dataClassClubNames.venue = binding.venue.text.toString()
            dataClassClubNames.date = binding.date.text.toString()
            app.placemarks.update(dataClassClubNames.copy())
            setResult(RESULT_OK)
            finish()
        }
         */
        }

        fun onDeleteClick(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.delete -> {
                    val launcherIntent = Intent(this, CreateFixtures::class.java)
                    //  getResult.launch(launcherIntent)
                }
            }
            return super.onOptionsItemSelected(item)
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.menu_edit_delete, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.item_cancel -> {
                    finish()
                }
                R.id.edit -> {
                    val launcherIntent = Intent(this, CreateFixtures::class.java)
                    dataClassClubNames.team1 = binding.team.text.toString()
                    dataClassClubNames.team2 = binding.team2.text.toString()
                    dataClassClubNames.score1 = (binding.score1.text.toString()).toInt()
                    dataClassClubNames.score2 = (binding.score2.text.toString()).toInt()
                    dataClassClubNames.venue = binding.venue.text.toString()
                    dataClassClubNames.date = binding.date.text.toString()
                    app.fixtures.update(dataClassClubNames.copy())
                    setResult(RESULT_OK)
                    finish()
                }
                R.id.delete -> {
                    val launcherIntent = Intent(this, CreateFixtures::class.java)
                    app.fixtures.delete(dataClassClubNames.copy())
                    setResult(RESULT_FIRST_USER)
                    finish()
                }
            }
            return super.onOptionsItemSelected(item)
        }
    }
