package edu.khai.simonenko.mytvschedule.repository

import edu.khai.simonenko.mytvschedule.repository.model.ActorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActorRepository : JpaRepository<ActorEntity, Long>