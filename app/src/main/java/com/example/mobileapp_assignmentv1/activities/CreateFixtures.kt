package com.example.mobileapp_assignmentv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mobileapp_assignmentv1.R
import com.google.android.material.snackbar.Snackbar
import com.example.mobileapp_assignmentv1.models.Fixtures
import com.example.mobileapp_assignmentv1.databinding.CreatingFixtureBinding
import com.example.mobileapp_assignmentv1.main.Main


    class CreateFixtures : AppCompatActivity() {

        private lateinit var binding: CreatingFixtureBinding
        var dataClassFixtures = Fixtures()
        lateinit var app: Main

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            binding = CreatingFixtureBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.toolbarAdd.title = title
            setSupportActionBar(binding.toolbarAdd)
            //binding.btnDelete.isVisible = false
            //R.id.delete.isVisible = false
            //R.id.update.isVisible = false
            app = application as Main

            if (intent.hasExtra("Edit")) {
                dataClassFixtures = intent.extras?.getParcelable("Edit")!!
                binding.team.setText(dataClassFixtures.team1)
                // binding.btnDelete.isVisible = true
                //R.id.delete.isVisible = true
                //R.id.update.isVisible = true
            }

            binding.btnAdd.setOnClickListener() {
                dataClassFixtures.team1 = binding.team.text.toString()
                dataClassFixtures.team2 = binding.team2.text.toString()
                dataClassFixtures.score1 = (binding.homeScoreSpinner.toString()).toInt()
                dataClassFixtures.score2 = (binding.score2.text.toString()).toInt()
                dataClassFixtures.venue = binding.venue.text.toString()
                dataClassFixtures.date = binding.date.text.toString()
                if (dataClassFixtures.team1.isNotEmpty()) {
                    app.fixtures.create(dataClassFixtures.copy())
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Snackbar
                        .make(it, "Please Enter a title", Snackbar.LENGTH_LONG)
                        .show()
                }
            }

            val spinner: Spinner = binding.homeScoreSpinner
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                this,
                R.array.spinner_options,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
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
                    dataClassFixtures.team1 = binding.team.text.toString()
                    dataClassFixtures.team2 = binding.team2.text.toString()
                    dataClassFixtures.score1 = (binding.score1.text.toString()).toInt()
                    dataClassFixtures.score2 = (binding.score2.text.toString()).toInt()
                    dataClassFixtures.venue = binding.venue.text.toString()
                    dataClassFixtures.date = binding.date.text.toString()
                    app.fixtures.update(dataClassFixtures.copy())
                    setResult(RESULT_OK)
                    finish()
                }
                R.id.delete -> {
                    val launcherIntent = Intent(this, CreateFixtures::class.java)
                    app.fixtures.delete(dataClassFixtures.copy())
                    setResult(RESULT_FIRST_USER)
                    finish()
                }
            }
            return super.onOptionsItemSelected(item)
        }
    }
