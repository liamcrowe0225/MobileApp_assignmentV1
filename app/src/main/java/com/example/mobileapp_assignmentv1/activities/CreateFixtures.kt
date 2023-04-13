package com.example.mobileapp_assignmentv1.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mobileapp_assignmentv1.R
import com.example.mobileapp_assignmentv1.models.Fixtures
import com.example.mobileapp_assignmentv1.databinding.CreatingFixtureBinding
import com.example.mobileapp_assignmentv1.helpers.showImagePicker
import com.example.mobileapp_assignmentv1.main.Main
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.i
import timber.log.Timber.log

class CreateFixtures : AppCompatActivity() {

        private lateinit var binding: CreatingFixtureBinding
        var dataClassFixtures = Fixtures()
        lateinit var app: Main
        private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
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
                    //log(1, "HERE I AM")
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

            binding.chooseImage.setOnClickListener {
                btnImage=1
                showImagePicker(imageIntentLauncher,this)
            }
            binding.chooseImage2.setOnClickListener {
                btnImage=2
                showImagePicker(imageIntentLauncher,this)
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
                                binding.chooseImage.setText("change_image")
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
                                binding.chooseImage2.setText("change_image2")
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
                    dataClassFixtures.date = binding.date.text.toString()
                    if (dataClassFixtures.image != Uri.EMPTY) {
                        binding.chooseImage.setText("change_image")
                    }
                    if (dataClassFixtures.image2 != Uri.EMPTY) {
                        binding.chooseImage2.setText("change_image2")
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
