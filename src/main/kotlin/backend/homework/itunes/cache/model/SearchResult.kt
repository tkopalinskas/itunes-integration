package backend.homework.itunes.cache.model

import backend.homework.itunes.model.ArtistSearchResponse
import java.time.LocalDateTime

data class SearchResult(
    val lastUpdated: LocalDateTime,
    val searchResponse: ArtistSearchResponse,
)
