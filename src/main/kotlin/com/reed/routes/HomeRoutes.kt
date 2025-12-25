package com.reed.routes

import com.reed.models.HomeResponse
import com.reed.repository.ArtistRepository
import com.reed.repository.HomeRepository
import com.reed.repository.SongRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.homeRoutes() {
    val artistRepository = ArtistRepository()
    val songRepository = SongRepository(artistRepository)
    val repository = HomeRepository(songRepository, artistRepository)

    route("/home") {
        get {
            val homeData = repository.getHomeData()
            call.respond(homeData)
        }
    }
}
