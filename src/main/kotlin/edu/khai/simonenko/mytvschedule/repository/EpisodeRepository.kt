package edu.khai.simonenko.mytvschedule.repository

import edu.khai.simonenko.mytvschedule.repository.model.EpisodeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EpisodeRepository : JpaRepository<EpisodeEntity, Long>