package backend.homework.itunes.service

import backend.homework.itunes.api.ItunesApi
import backend.homework.itunes.cache.SearchCache
import backend.homework.itunes.cache.model.SearchResult
import backend.homework.itunes.exception.PersistenceException
import backend.homework.itunes.model.AlbumResponse
import backend.homework.itunes.model.ArtistSearchResponse
import backend.homework.itunes.model.SaveFavoriteArtistRequest
import backend.homework.itunes.repository.AlbumRepository
import backend.homework.itunes.repository.FavoriteArtistRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArtistService(
    val albumRepository: AlbumRepository,
    val favoriteArtistRepository: FavoriteArtistRepository,
    val searchCache: SearchCache,
    val itunesApi: ItunesApi
) {
    fun searchFavoriteArtist(artistName: String): ArtistSearchResponse {
        val cachedResult = searchCache.find(artistName)
        val searchResult = if (
            cachedResult == null ||
            cachedResult.lastUpdated.isAfter(cachedResult.lastUpdated.plusHours(2))
        ) {
            val itunesResult = itunesApi.searchArtistByName(artistName)
            val itunesSearchResult = SearchResult(LocalDateTime.now(), itunesResult)
            searchCache.save(artistName, itunesSearchResult)
            itunesSearchResult
        } else {
            cachedResult
        }
        return searchResult.searchResponse
    }

    fun saveArtist(saveFavoriteArtistRequest: SaveFavoriteArtistRequest) {
        favoriteArtistRepository.save(saveFavoriteArtistRequest.userId, saveFavoriteArtistRequest.artistId)
    }

    fun getFavoriteArtistAlbums(userId: Long): List<AlbumResponse.AlbumResult> {
        val favoriteArtistId = favoriteArtistRepository.findFavoriteArtist(userId)
        val albums = try {
            albumRepository.findAlbumsByArtistId(favoriteArtistId)
        } catch (e: PersistenceException) {
            val receivedAlbums = itunesApi.receiveAlbums(favoriteArtistId)
            albumRepository.save(favoriteArtistId, receivedAlbums.results)
            receivedAlbums.results
        }
        return albums
    }
}