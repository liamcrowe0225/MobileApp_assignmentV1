package com.example.mobileapp_assignmentv1.models

import timber.log.Timber.i

var lastId = 0L
internal fun getId(): Long {
    return lastId++
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

    override fun update(club: Fixtures) {
        var foundCriteria: Fixtures? = fixture.find { p -> p.idClub == club.idClub }
        if (foundCriteria != null) {
            foundCriteria.team1 = club.team1
            foundCriteria.team2 = club.team2
            foundCriteria.score1 = club.score1
            foundCriteria.score2 = club.score2
            foundCriteria.venue = club.venue
            foundCriteria.date = club.date
            foundCriteria.image = club.image
            foundCriteria.image2 = club.image2
            logAll()
        }
    }

    private fun logAll() {
        fixture.forEach { i("$it") }
    }
}