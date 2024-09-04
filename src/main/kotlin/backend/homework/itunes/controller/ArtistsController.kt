package backend.homework.itunes.controller

import backend.homework.itunes.exception.ItunesApiException
import backend.homework.itunes.model.SaveFavoriteArtistRequest
import backend.homework.itunes.service.ArtistService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ArtistsController(
    val artistService: ArtistService
) {

    /*Response could remove not required information but in this case it is not stated what is required*/
    @GetMapping("/artists/search/{artistName}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun searchFavoriteArtist(@PathVariable artistName: String): ResponseEntity<Any> {
        // Exception handling could have been done in @ControllerAdvice but in this case my preference is to do it in controller
        return try {
            ResponseEntity.ok(artistService.searchFavoriteArtist(artistName.lowercase()))
        } catch (e: ItunesApiException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/artists/save")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun saveArtist(@RequestBody saveFavoriteArtistRequest: SaveFavoriteArtistRequest) {
        artistService.saveArtist(saveFavoriteArtistRequest)
    }

    @GetMapping("/artists/albums/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getFavoriteArtistAlbums(@PathVariable userId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(artistService.getFavoriteArtistAlbums(userId))
        } catch (e: Throwable) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}