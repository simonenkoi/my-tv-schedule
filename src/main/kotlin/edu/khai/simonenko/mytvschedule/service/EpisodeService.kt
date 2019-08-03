package edu.khai.simonenko.mytvschedule.service

import edu.khai.simonenko.mytvschedule.api.model.Episode
import edu.khai.simonenko.mytvschedule.common.Logging
import edu.khai.simonenko.mytvschedule.common.logger
import edu.khai.simonenko.mytvschedule.repository.EpisodeRepository
import edu.khai.simonenko.mytvschedule.repository.model.EpisodeEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
open class EpisodeService(val episodeRepository: EpisodeRepository) : Logging {

    @Transactional
    fun saveAll(episodes: Collection<Episode>?): List<EpisodeEntity> {
        return episodes?.distinctBy { it.id }
            ?.also { logger().info("Saving {} episodes", episodes.size) }
            ?.map { createOrUpdate(it) }
            ?.also { episodeRepository.saveAll(it) }
            .orEmpty()
    }

    private fun createOrUpdate(episode: Episode): EpisodeEntity {
        return (episodeRepository.findByExternalId(episode.id)
            ?: EpisodeEntity(externalId = episode.id))
            .apply { update(episode) }
    }

    @Transactional
    fun markAsWatched(externalIds: List<Long>): Flux<EpisodeEntity> {
        return episodeRepository.findByExternalIdIn(externalIds)
            .also { logger().info("Marking {} episodes as watched", it.size) }
            .mapNotNull { it.markAsWatched() }
            .let { Flux.fromIterable(it) }
    }

    @Transactional
    fun markAsWatched(externalId: Long): Mono<EpisodeEntity> {
        logger().info("Marking {} episode as watched", externalId)
        return episodeRepository.findByExternalId(externalId)
            .also { it.markAsWatched() }
            .let { Mono.justOrEmpty(it) }
    }

    @Transactional(readOnly = true)
    fun findFirstUnwatchedOfEachShow(): Flux<EpisodeEntity> {
        logger().info("Finding first unwatched episode of each shoe")
        return episodeRepository.findFirstUnwatchedOfEachShow()
            .let { Flux.fromIterable(it) }
    }

    private fun EpisodeEntity?.markAsWatched(): EpisodeEntity? {
        return this?.apply { watched = true }
    }

    private fun EpisodeEntity.update(episode: Episode): EpisodeEntity {
        return this.apply {
            name = episode.name
            season = episode.season
            number = episode.number
            airdate = episode.airdate
            watched = episode.watched
        }
    }
}