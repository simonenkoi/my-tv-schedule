package edu.khai.simonenko.mytvschedule.repository.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "episodes")
data class EpisodeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
    val externalId: Long,
    var name: String? = null,
    var season: Int? = null,
    var number: Int? = null,
    var airdate: LocalDate? = null,
    var watched: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    val show: ShowEntity? = null
)