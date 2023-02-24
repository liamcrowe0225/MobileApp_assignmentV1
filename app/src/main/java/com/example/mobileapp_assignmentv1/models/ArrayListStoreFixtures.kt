package com.example.mobileapp_assignmentv1.models

import timber.log.Timber.i

var Id = 0L
internal fun getId(): Long {
    return Id++
}
class ArrayListStoreFixtures : FixturesArrayFunCall {

    val fixture = ArrayList<Fixtures>()

    override fun findAll(): List<Fixtures> {
        return fixture
    }

    override fun create(fixtures: Fixtures) {
        fixtures.idClub = getId()
        fixture.add(fixtures)
        logAll()
    }

    override fun delete(club: Fixtures) {
        //use the id to find it call actualClub
        var actualClub: Fixtures? = fixture.find { p -> p.idClub == club.idClub }
        fixture.remove(actualClub)
    }

    override fun update(placemark: Fixtures) {
        var foundCriteria: Fixtures? = fixture.find { p -> p.idClub == placemark.idClub }
        if (foundCriteria != null) {
            foundCriteria.team1 = placemark.team1
            foundCriteria.team2 = placemark.team2
            foundCriteria.score1 = placemark.score1
            foundCriteria.score2 = placemark.score2
            foundCriteria.venue = placemark.venue
            foundCriteria.date = placemark.date
            logAll()
        }
    }

    private fun logAll() {
        fixture.forEach { i("$it") }
    }
}