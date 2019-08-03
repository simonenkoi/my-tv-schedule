package edu.khai.simonenko.mytvschedule.service

import edu.khai.simonenko.mytvschedule.api.client.ShowEndpoint
import edu.khai.simonenko.mytvschedule.repository.ShowRepository
import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import edu.khai.simonenko.mytvschedule.repository.model.fromShow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
open class ShowService(
    val showRepository: ShowRepository,
    val showEndpoint: ShowEndpoint
) {

    @Transactional
    fun saveShow(id: Long): Mono<ShowEntity> {
        return showEndpoint.getShowById(id)
            .map { ShowEntity.fromShow(it) }
            .map { showRepository.save(it) }
    }
}