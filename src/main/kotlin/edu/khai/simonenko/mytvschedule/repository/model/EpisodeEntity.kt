package edu.khai.simonenko.mytvschedule.repository.model

import java.time.LocalDate
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "episodes")
data class EpisodeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
    val externalId: Long,
    val name: String? = null,
    val season: Int? = null,
    val number: Int? = null,
    val airdate: LocalDate? = null,
    val watched: Boolean,
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.LAZY)
    val movie: MovieEntity? = null
) {
    companion object
}