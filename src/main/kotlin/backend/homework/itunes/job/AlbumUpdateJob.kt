package backend.homework.itunes.job

import backend.homework.itunes.api.ItunesApi
import backend.homework.itunes.repository.AlbumRepository
import org.springframework.stereotype.Component

@Component
class AlbumUpdateJob(
    private val albumRepository: AlbumRepository,
    private val itunesApi: ItunesApi
) : Runnable {
    override fun run() {
        val allSavedArtists = albumRepository.getAllSavedAlbumArtistIds()
        val receiveMultipleArtistAlbums = itunesApi.receiveMultipleArtistAlbums(allSavedArtists)
        receiveMultipleArtistAlbums.results
            .groupBy { it.amgArtistId }
            .forEach { (artistId, result) -> albumRepository.save(artistId, result)}
    }
}