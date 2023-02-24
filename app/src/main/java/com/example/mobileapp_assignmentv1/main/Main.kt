package com.example.mobileapp_assignmentv1.main

import android.app.Application
import com.example.mobileapp_assignmentv1.models.ArrayListStoreFixtures
import timber.log.Timber

class Main : Application() {
    val fixtures = ArrayListStoreFixtures()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}