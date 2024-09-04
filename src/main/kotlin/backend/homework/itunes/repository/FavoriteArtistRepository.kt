package backend.homework.itunes.repository

import backend.homework.itunes.exception.NoArtistSavedException
import org.springframework.stereotype.Component

@Component
class FavoriteArtistRepository {

    private var favoriteArtistDatabase: MutableMap<Long, Long> = mutableMapOf()

    fun save(userId: Long, artistId: Long) {
        favoriteArtistDatabase[userId] = artistId
    }
    fun findFavoriteArtist(userId: Long): Long {
        return favoriteArtistDatabase[userId]?: throw NoArtistSavedException("User $userId got no favorite artist")
    }
}