package edu.khai.simonenko.mytvschedule.repository

import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ShowRepository : JpaRepository<ShowEntity, Long>, JpaSpecificationExecutor<ShowEntity>