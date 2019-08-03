package edu.khai.simonenko.mytvschedule.repository.model

import org.hibernate.annotations.Formula
import java.time.LocalDate
import javax.persistence.Basic
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "shows")
data class ShowEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
    val externalId: Long,
    var name: String? = null,
    var image: String? = null,

    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "show_actor",
        joinColumns = [JoinColumn(name = "show_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")]
    )
    var cast: List<ActorEntity> = emptyList(),

    @OneToMany(cascade = [CascadeType.MERGE], orphanRemoval = true)
    @JoinColumn(name = "show_id")
    var episodes: List<EpisodeEntity> = emptyList(),

    @Basic(fetch = FetchType.LAZY)
    @Formula(
        "(SELECT e.airdate " +
                "FROM episodes e " +
                "WHERE e.show_id = id " +
                "ORDER BY e.airdate LIMIT 1)"
    )
    val firstEpisodeAirDate: LocalDate? = null,

    @Basic(fetch = FetchType.LAZY)
    @Formula(
        "(SELECT e.airdate " +
                "FROM episodes e " +
                "WHERE e.show_id = id AND e.watched = false " +
                "ORDER BY e.airdate LIMIT 1)"
    )
    val firstUnwatchedEpisodeAirDate: LocalDate? = null
)