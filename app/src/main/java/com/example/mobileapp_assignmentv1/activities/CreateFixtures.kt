package com.example.mobileapp_assignmentv1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import com.example.mobileapp_assignmentv1.R
import com.example.mobileapp_assignmentv1.models.Fixtures
import com.example.mobileapp_assignmentv1.databinding.CreatingFixtureBinding
import com.example.mobileapp_assignmentv1.main.Main
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i

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
                binding.team2.setText(dataClassFixtures.team2)
                binding.homeScoreSpinner.setSelection(dataClassFixtures.score1)
                binding.awayScoreSpinner.setSelection(dataClassFixtures.score2)
                binding.venue.setText(dataClassFixtures.venue)
                binding.date.setText(dataClassFixtures.date)
                // binding.btnDelete.isVisible = true
                //R.id.delete.isVisible = true
                //R.id.update.isVisible = true
            }

            binding.btnAdd.setOnClickListener() {
                dataClassFixtures.team1 = binding.team.text.toString()
                dataClassFixtures.team2 = binding.team2.text.toString()
                dataClassFixtures.score1 = binding.homeScoreSpinner.selectedItem.toString().toInt()
                dataClassFixtures.score2 = binding.awayScoreSpinner.selectedItem.toString().toInt()
                dataClassFixtures.venue = binding.venue.text.toString()
                dataClassFixtures.date = binding.date.text.toString()
                if (dataClassFixtures.team1.isNotEmpty()  && dataClassFixtures.team2.isNotEmpty() &&
                     binding.homeScoreSpinner.selectedItem.toString() != "Score" &&
                    binding.awayScoreSpinner.selectedItem.toString() != "Score"&&
                    dataClassFixtures.venue.isNotEmpty() && dataClassFixtures.date.isNotEmpty()) {
                    app.fixtures.create(dataClassFixtures.copy())
                    setResult(RESULT_OK)
                    finish()
                } else {
                   // i("Please fill in the remaining fields")
                    Snackbar
                        .make(it,"Please fill in remaining fields", Snackbar.LENGTH_LONG)
                        .show()
                }
            }

            //Reference: https://www.geeksforgeeks.org/spinner-in-android-using-java-with-example/
            val homeSpinner: Spinner = binding.homeScoreSpinner
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                this,
                R.array.spinner_score,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                homeSpinner.adapter = adapter
            }
            val awaySpinner: Spinner = binding.awayScoreSpinner
            ArrayAdapter.createFromResource(
                this,
                R.array.spinner_score,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                awaySpinner.adapter = adapter
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
                    dataClassFixtures.score1 = binding.homeScoreSpinner.selectedItem.toString().toInt()
                    dataClassFixtures.score2 = binding.awayScoreSpinner.selectedItem.toString().toInt()
                    dataClassFixtures.venue = binding.venue.text.toString()
                    dataClassFixtures.date = binding.date.text.toString()
               /*     if (dataClassFixtures.team1.isNotEmpty()  && dataClassFixtures.team2.isNotEmpty() &&
                        binding.homeScoreSpinner.selectedItem.toString() != "Score" &&
                        binding.awayScoreSpinner.selectedItem.toString() != "Score"&&
                        dataClassFixtures.venue.isNotEmpty() && dataClassFixtures.date.isNotEmpty()) { */
                        app.fixtures.update(dataClassFixtures.copy())
                        setResult(RESULT_OK)
                        finish()
                /*else {
                        // i("Please fill in the remaining fields")
                        Snackbar
                            .make(it,"Please fill in remaining fields", Snackbar.LENGTH_LONG)
                            .show()
                    }
                 */
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
