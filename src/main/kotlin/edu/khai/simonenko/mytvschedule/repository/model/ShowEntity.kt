package edu.khai.simonenko.mytvschedule.repository.model

import javax.persistence.CascadeType
import javax.persistence.Entity
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
    val name: String? = null,
    val image: String? = null,
    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "show_actor",
        joinColumns = [JoinColumn(name = "show_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")]
    )
    var cast: MutableList<ActorEntity> = mutableListOf(),
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    @JoinColumn(name = "show_id")
    var episodes: MutableList<EpisodeEntity> = mutableListOf()
) {
    companion object
}