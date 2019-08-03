package edu.khai.simonenko.mytvschedule.repository

import edu.khai.simonenko.mytvschedule.repository.model.EpisodeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EpisodeRepository : JpaRepository<EpisodeEntity, Long> {

    fun findByExternalId(externalId: Long): EpisodeEntity?

    fun findByExternalIdIn(externalIds: List<Long>): List<EpisodeEntity>

    @Query(
        "WITH summary AS ( " +
                "    SELECT e.id, " +
                "           ROW_NUMBER() OVER(PARTITION BY e.show_id  " +
                "                                 ORDER BY e.airdate ASC) AS rk " +
                "      FROM episodes e " +
                "      WHERE e.watched = false) " +
                "SELECT * " +
                "  FROM episodes e " +
                "  WHERE e.id IN (SELECT s.id " +
                "                   FROM summary s " +
                "                   WHERE s.rk = 1)", nativeQuery = true
    )
    fun findFirstUnwatchedOfEachShow(): List<EpisodeEntity>
}