package edu.khai.simonenko.mytvschedule.api.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Episode(
    val id: Long,
    val name: String? = null,
    val season: Int? = null,
    val number: Int? = null,
    val airdate: LocalDate? = null,
    val watched: Boolean = false,
    var show: Show? = null
)