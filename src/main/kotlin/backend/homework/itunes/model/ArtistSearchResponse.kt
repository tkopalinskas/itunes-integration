package backend.homework.itunes.model

data class ArtistSearchResponse(
    val resultCount: Int,
    val results: List<ArtistResult>
){
    data class ArtistResult(
        val wrapperType: String,
        val artistType: String, // Could be enum,
        val artistName: String,
        val artistLinkUrl: String,
        val artistId: Long,
        val amgArtistId: Long,
        val primaryGenreName: String?, // Could be Enum,
        val primaryGenreId: Long,
    )
}