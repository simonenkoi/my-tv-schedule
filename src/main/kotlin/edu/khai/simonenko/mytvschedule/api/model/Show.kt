package edu.khai.simonenko.mytvschedule.api.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Show(
    val id: Long,
    val name: String? = null,
    val image: String? = null,
    var cast: List<Actor>? = null,
    var episodes: List<Episode>? = null
)