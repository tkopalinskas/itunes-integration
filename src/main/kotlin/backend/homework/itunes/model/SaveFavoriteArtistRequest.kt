package backend.homework.itunes.model

data class SaveFavoriteArtistRequest(
    val userId: Long,
    val artistId: Long,
)
