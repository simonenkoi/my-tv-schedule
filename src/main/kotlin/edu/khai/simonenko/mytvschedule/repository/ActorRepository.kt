package edu.khai.simonenko.mytvschedule.repository

import edu.khai.simonenko.mytvschedule.repository.model.ActorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ActorRepository : JpaRepository<ActorEntity, Long> {

    fun findByExternalId(externalId: Long): ActorEntity?

    @Query(
        "SELECT external_id " +
                "FROM actors " +
                "WHERE id IN (" +
                "  SELECT actor_id " +
                "    FROM show_actor " +
                "    GROUP BY actor_id " +
                "    ORDER BY count(actor_id) DESC" +
                ")",
        nativeQuery = true
    )
    fun findMostFrequentActorExternalIds(): List<Long>
}