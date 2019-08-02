package edu.khai.simonenko.mytvschedule.api.model.tvmaze

data class TvMazePerson(
    val id: Long,
    val name: String? = null,
    val image: TvMazeImage? = null
)