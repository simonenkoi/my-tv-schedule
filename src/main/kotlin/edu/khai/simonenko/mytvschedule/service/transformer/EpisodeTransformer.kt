package edu.khai.simonenko.mytvschedule.service.transformer

import edu.khai.simonenko.mytvschedule.api.model.Episode
import edu.khai.simonenko.mytvschedule.configuration.annotation.Transformer
import edu.khai.simonenko.mytvschedule.repository.model.toEpisodeEager
import edu.khai.simonenko.mytvschedule.service.EpisodeService
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Transformer
open class EpisodeTransformer(val episodeService: EpisodeService) {

    @Transactional(readOnly = true)
    fun findFirstUnwatchedOfEachShow(): Flux<Episode> {
        return episodeService.findFirstUnwatchedOfEachShow().map { it.toEpisodeEager(fetchShow = true) }
    }

    @Transactional
    fun markAsWatched(id: Long): Mono<Episode> {
        return episodeService.markAsWatched(id).map { it.toEpisodeEager() }
    }
}