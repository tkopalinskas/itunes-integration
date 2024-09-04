package backend.homework.itunes.api

import backend.homework.itunes.exception.ItunesApiException
import backend.homework.itunes.model.AlbumResponse
import backend.homework.itunes.model.ArtistSearchResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.nio.charset.StandardCharsets

@Component
class ItunesApi(
    private val restClient: RestClient,
    private val mapper: ObjectMapper
) {

    fun searchArtistByName(artistName: String): ArtistSearchResponse {
        val response = restClient.get()
            .uri("/search?entity=allArtist&term=${artistName}")
            .retrieve()
            .body(ByteArray::class.java)
        val jsonString = response?.let { String(it, StandardCharsets.UTF_8) }
            ?: throw ItunesApiException("Failed to find artist by artist name")
        return mapper.readValue(jsonString, ArtistSearchResponse::class.java)
    }

    fun receiveAlbums(artistId: Long): AlbumResponse {
        val response = restClient.get()
            .uri("/lookup?amgArtistId=${artistId}&entity=album&limit=5")
            .retrieve()
            .body(ByteArray::class.java)
        val jsonString = response?.let { String(it, StandardCharsets.UTF_8) }
            ?: throw ItunesApiException("Failed to find albums by artistId")
        val sanitisedResponse = removeNotNeededWrapperType(jsonString, "artist")
        return mapper.readValue(sanitisedResponse, AlbumResponse::class.java)
    }

    fun receiveMultipleArtistAlbums(artistIds: MutableSet<Long>): AlbumResponse {
        val response = restClient.get()
            .uri("/lookup?amgArtistId=${artistIds.joinToString(separator = ",")}&entity=album&limit=5")
            .retrieve()
            .body(ByteArray::class.java)
        val jsonString = response?.let { String(it, StandardCharsets.UTF_8) }
            ?: throw ItunesApiException("Failed to find albums by artistId")
        val sanitisedResponse = removeNotNeededWrapperType(jsonString, "artist")
        return mapper.readValue(sanitisedResponse, AlbumResponse::class.java)

    }

    fun removeNotNeededWrapperType(response: String, wrapperType: String): String {
        val tree = mapper.readTree(response)
        tree.get(RESULTS).removeAll { jsonNode -> jsonNode.get(WRAPPER_TYPE).asText() == wrapperType }
        return tree.toString()
    }

}

const val RESULTS = "results"
const val WRAPPER_TYPE = "wrapperType"