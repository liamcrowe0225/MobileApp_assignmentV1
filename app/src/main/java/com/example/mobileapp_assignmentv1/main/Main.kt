package com.example.mobileapp_assignmentv1.main

import android.R
import android.app.Application
import android.widget.ArrayAdapter
import com.example.mobileapp_assignmentv1.models.ArrayListStoreFixtures
import com.example.mobileapp_assignmentv1.models.FixtureJSON
import com.example.mobileapp_assignmentv1.models.FixturesArrayFunCall
import timber.log.Timber


class Main : Application() {
  //  var fixtures = ArrayListStoreFixtures()
    lateinit var fixtures: FixturesArrayFunCall
    override fun onCreate() {
        super.onCreate()
        fixtures = FixtureJSON(applicationContext)
        Timber.plant(Timber.DebugTree())
    }
}