package edu.khai.simonenko.mytvschedule.repository.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "actors")
data class ActorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
    val externalId: Long,
    var name: String? = null,
    var image: String? = null,

    @ManyToMany(mappedBy = "cast")
    val shows: List<ShowEntity> = emptyList()
)