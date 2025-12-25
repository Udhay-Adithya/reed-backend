package com.reed

import com.reed.config.JwtConfig
import com.reed.database.DatabaseConfig
import com.reed.database.DatabaseSeeder
import com.reed.database.PlaylistSongs
import com.reed.database.Playlists
import com.reed.models.ErrorResponse
import com.reed.models.UserPrincipal
import com.reed.plugins.configureCORS
import com.reed.repository.ArtistRepository
import com.reed.repository.HomeRepository
import com.reed.repository.PlaylistRepository
import com.reed.repository.SongRepository
import com.reed.repository.UserRepository
import com.reed.routes.artistRoutes
import com.reed.routes.authRoutes
import com.reed.routes.homeRoutes
import com.reed.routes.playlistRoutes
import com.reed.routes.songRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    // Initialize the database
    val dbConfig = DatabaseConfig()
    dbConfig.init()

    // Create tables
    transaction {
        SchemaUtils.create(
            com.reed.database.Users,
            com.reed.database.Artists,
            com.reed.database.Songs,
            Playlists,
            PlaylistSongs,
            com.reed.database.UserPreferences,
            com.reed.database.UserListeningHistory
        )
    }

    // Initialize repositories
    val userRepository = UserRepository()
    val artistRepository = ArtistRepository()
    val songRepository = SongRepository(artistRepository)
    val homeRepository = HomeRepository(artistRepository=artistRepository, songRepository = songRepository)
    val playlistRepository = PlaylistRepository(songRepository)

    // Seed the database with sample data
    val seeder = DatabaseSeeder(artistRepository, songRepository, playlistRepository, userRepository)
    seeder.seed()

    // JWT Configuration
    val jwtConfig = JwtConfig()
    
    // Configure authentication
    install(Authentication) {
        jwt("jwt") {
            realm = jwtConfig.realm
            verifier(jwtConfig.verifier)
            validate { credential ->
                try {
                    if (credential.payload.audience.contains(jwtConfig.audience)) {
                        val id = credential.payload.getClaim("id").asString()
                        val email = credential.payload.getClaim("email").asString()
                        if (id != null && email != null) {
                            UserPrincipal(id)
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    application.log.error("JWT validation error: ${e.message}")
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(
                    HttpStatusCode.Unauthorized, 
                    ErrorResponse("Token is not valid or has expired. Please provide a valid Bearer token.")
                )
            }
        }
    }

    configureSecurity()
    configureFrameworks()
    configureSerialization()
    configureHTTP()
    configureRouting()
    configureCORS()

    // Configure Routing
    routing {
        authRoutes(userRepository, jwtConfig)
        authenticate("jwt"){
            artistRoutes()
            songRoutes()
            homeRoutes()
            playlistRoutes(playlistRepository)
        }

    }
}
