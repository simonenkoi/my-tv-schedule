package edu.khai.simonenko.mytvschedule.api.model.tvmaze

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate


data class TvMazeEpisode(
    val id: Long,
    val name: String? = null,
    val season: Int? = null,
    val number: Int? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val airdate: LocalDate? = null
)