package edu.khai.simonenko.mytvschedule.api.model

import java.time.LocalDate

data class Episode(
    val id: Long,
    val name: String? = null,
    val season: Int? = null,
    val number: Int? = null,
    val airdate: LocalDate? = null,
    val watched: Boolean = false
)