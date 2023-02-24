package com.example.mobileapp_assignmentv1.models

interface FixturesArrayFunCall {
    fun findAll(): List<Fixtures>
    fun create(placemark: Fixtures)
    fun update(placemark: Fixtures)
    fun delete(club: Fixtures)
}