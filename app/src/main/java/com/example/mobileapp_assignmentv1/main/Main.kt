package com.example.mobileapp_assignmentv1.main

import com.example.mobileapp_assignmentv1.models.ArrayListStoreFixtures
import timber.log.Timber

class Main {
    val fixtures = ArrayListStoreFixtures()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
//        placemarks.add(PlacemarkModel("One", "About one..."))
//        placemarks.add(PlacemarkModel("Two", "About two..."))
//        placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}