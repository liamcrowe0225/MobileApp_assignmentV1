package com.example.mobileapp_assignmentv1.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mobileapp_assignmentv1.R
import com.example.mobileapp_assignmentv1.models.Fixtures
import com.example.mobileapp_assignmentv1.databinding.CreatingFixtureBinding
import com.example.mobileapp_assignmentv1.helpers.showImagePicker
import com.example.mobileapp_assignmentv1.main.Main
import com.example.mobileapp_assignmentv1.models.Location
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.i
import timber.log.Timber.log
import java.text.SimpleDateFormat
import java.util.*

class CreateFixtures : AppCompatActivity() {

        private lateinit var binding: CreatingFixtureBinding
        var dataClassFixtures = Fixtures()
        lateinit var app: Main
        private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
        private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
        var btnImage = 1
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
            registerMapCallback()
           binding.calendarView.visibility = View.GONE
            if (intent.hasExtra("Edit")) {
                dataClassFixtures = intent.extras?.getParcelable("Edit")!!
                binding.team.setText(dataClassFixtures.team1)
                binding.team2.setText(dataClassFixtures.team2)
                binding.homeScoreSpinner.setSelection(dataClassFixtures.score1)
                binding.awayScoreSpinner.setSelection(dataClassFixtures.score2)
                binding.venue.setText(dataClassFixtures.venue)
                binding.venue.setText(dataClassFixtures.date)
            }

            binding.btnAdd.setOnClickListener() {
                dataClassFixtures.team1 = binding.team.text.toString()
                dataClassFixtures.team2 = binding.team2.text.toString()
                dataClassFixtures.score1 = binding.homeScoreSpinner.selectedItem.toString().toInt()
                dataClassFixtures.score2 = binding.awayScoreSpinner.selectedItem.toString().toInt()
                dataClassFixtures.venue = binding.venue.text.toString()
                dataClassFixtures.date = binding.dateBtn.text.toString()
                if (dataClassFixtures.team1.isNotEmpty()  && dataClassFixtures.team2.isNotEmpty() &&
                     binding.homeScoreSpinner.selectedItem.toString() != "Score" &&
                    binding.awayScoreSpinner.selectedItem.toString() != "Score"&&
                    dataClassFixtures.venue.isNotEmpty()) {
                    app.fixtures.create(dataClassFixtures.copy())
                    setResult(RESULT_OK)
                    //log(1, "HERE I AM")
                    finish()
                } else {
                   // i("Please fill in the remaining fields")
                    Snackbar
                        .make(it,"Please fill in remaining fields", Snackbar.LENGTH_LONG)
                        .show()
                }
            }

            //https://www.geeksforgeeks.org/calendar-view-app-in-android-with-kotlin/
                binding.calendarView
                    .setOnDateChangeListener(
                        CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->

                            val Date = (dayOfMonth.toString() + "-"
                                    + (month + 1) + "-" + year)

                            // set this date in TextView for Display
                            binding.dateBtn.setText(Date)
                        })
                binding.dateBtn.setOnClickListener() {
                    if (binding.calendarView.visibility == View.VISIBLE) {
                        // Calendar view is already visible, so hide it
                        binding.calendarView.visibility = View.GONE
                        binding.calendarView.elevation = 0F
                    } else {
                        // Calendar view is hidden, so show it
                        binding.calendarView.visibility = View.VISIBLE
                        binding.calendarView.elevation = 5F
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

            binding.chooseImage.setOnClickListener {
                btnImage=1
                showImagePicker(imageIntentLauncher,this)
            }
            binding.chooseImage2.setOnClickListener {
                btnImage=2
                showImagePicker(imageIntentLauncher,this)
            }

            binding.locationBtn.setOnClickListener {
                val location = Location(52.245696, -7.139102, 15f)
                if (dataClassFixtures.zoom != 0f) {
                    location.lat =  dataClassFixtures.lat
                    location.lng = dataClassFixtures.lng
                    location.zoom = dataClassFixtures.zoom
                }
                val launcherIntent = Intent(this, MapActivity::class.java)
                    .putExtra("location", location)
                mapIntentLauncher.launch(launcherIntent)
            }
            registerImagePickerCallback()
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

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            dataClassFixtures.lat = location.lat
                            dataClassFixtures.lng = location.lng
                            dataClassFixtures.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            //If image button is equal to one,add image else add image 2
                            if (btnImage == 1) {
                                val image = result.data!!.data!!
                                contentResolver.takePersistableUriPermission(
                                    image,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                                dataClassFixtures.image = image


                                Picasso.get()
                                    .load(dataClassFixtures.image)
                                    .into(binding.imageEdit)
                                binding.chooseImage.setText("Change Home Img")
                                //If image button is equal to one,add image else add image 2
                            }
                            else {
                                val image2 = result.data!!.data!!
                                contentResolver.takePersistableUriPermission(
                                    image2,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                                dataClassFixtures.image2 = image2

                                Picasso.get()
                                    .load(dataClassFixtures.image2)
                                    .into(binding.imageEdit2)
                                binding.chooseImage2.setText("Change Away Img")
                            }
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
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
                    dataClassFixtures.date = binding.dateBtn.text.toString()
                    if (dataClassFixtures.image != Uri.EMPTY) {
                        binding.chooseImage.setText("Change Home Img")
                    }
                    if (dataClassFixtures.image2 != Uri.EMPTY) {
                        binding.chooseImage2.setText("Change Away Img")
                    }
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
