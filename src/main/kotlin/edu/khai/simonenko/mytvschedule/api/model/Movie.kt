package edu.khai.simonenko.mytvschedule.api.model

data class Movie(
    val id: Long,
    val name: String? = null,
    val image: String? = null,
    val cast: List<Actor>? = null,
    val episodes: List<Episode>? = null
)