package edu.khai.simonenko.mytvschedule.api.model.tvmaze

import com.fasterxml.jackson.annotation.JsonProperty

data class TvMazeShow(
    val id: Long,
    val name: String? = null,
    val image: TvMazeImage? = null,
    @JsonProperty("_embedded")
    val embedded: TvMazeShowEmbedded? = null
)