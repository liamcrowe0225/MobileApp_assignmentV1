package com.example.mobileapp_assignmentv1.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.example.mobileapp_assignmentv1.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "fixtures.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<Fixtures>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FixtureJSON(private val context: Context) : FixturesArrayFunCall {
    var fixture = mutableListOf<Fixtures>()
    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }
    override fun findAll(): MutableList<Fixtures> {
        logAll()
        return fixture
    }

    override fun create(club: Fixtures) {
        club.idClub = generateRandomId()
        fixture.add(club)
        serialize()
    }
    override fun update(club: Fixtures) {
        val listFixtures = findAll() as ArrayList<Fixtures>
        var foundCriteria: Fixtures? = listFixtures.find { p -> p.idClub == club.idClub }
        if (foundCriteria != null) {
            foundCriteria.team1 = club.team1
            foundCriteria.team2 = club.team2
            foundCriteria.score1 = club.score1
            foundCriteria.score2 = club.score2
            foundCriteria.venue = club.venue
            foundCriteria.date = club.date
            foundCriteria.image = club.image
            foundCriteria.image2 = club.image2
        }
        serialize()
    }

    private fun serialize() {
        print("in serialize")
        val jsonString = gsonBuilder.toJson(fixture, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        fixture = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(placemark: Fixtures) {
        fixture.remove(placemark)
        serialize()
    }

    private fun logAll() {
        fixture.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}