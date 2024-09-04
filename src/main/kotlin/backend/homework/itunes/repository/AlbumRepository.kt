package backend.homework.itunes.repository

import backend.homework.itunes.exception.PersistenceException
import backend.homework.itunes.model.AlbumResponse
import org.springframework.stereotype.Component

@Component
class AlbumRepository {

    private var albumDatabase: MutableMap<Long, List<AlbumResponse.AlbumResult>> = mutableMapOf()

    fun save(artistId: Long, albumResults: List<AlbumResponse.AlbumResult>) {
        albumDatabase[artistId] = albumResults
    }

    fun findAlbumsByArtistId(artistId: Long): List<AlbumResponse.AlbumResult> {
        return albumDatabase[artistId]?: throw PersistenceException("No albums found by artistId: $artistId")
    }

    fun getAllSavedAlbumArtistIds(): MutableSet<Long> {
        return albumDatabase.keys
    }
}