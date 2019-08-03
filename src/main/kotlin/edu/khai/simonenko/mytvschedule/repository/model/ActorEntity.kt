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
    val name: String? = null,
    val image: String? = null,
    @ManyToMany(mappedBy = "cast")
    val shows: List<ShowEntity> = emptyList()
) {
    companion object
}