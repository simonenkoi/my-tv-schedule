package edu.khai.simonenko.mytvschedule.service

import edu.khai.simonenko.mytvschedule.api.client.ShowEndpoint
import edu.khai.simonenko.mytvschedule.api.model.Show
import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.common.Logging
import edu.khai.simonenko.mytvschedule.common.logger
import edu.khai.simonenko.mytvschedule.repository.ShowRepository
import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import edu.khai.simonenko.mytvschedule.repository.specification.ShowSpecification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
open class ShowService(
    val showRepository: ShowRepository,
    val showEndpoint: ShowEndpoint,
    val episodeService: EpisodeService,
    val actorService: ActorService
) : Logging {

    @Transactional
    fun save(id: Long): Mono<ShowEntity> {
        logger().info("Saving {} movie to schedule", id)
        return showEndpoint.getShowById(id)
            .flatMap { getFromModel(it) }
            .map { showRepository.save(it) }
    }

    private fun getFromModel(show: Show): Mono<ShowEntity> {
        return (showRepository.findByExternalId(show.id) ?: ShowEntity(externalId = show.id))
            .apply {
                name = show.name
                image = show.image
                cast = actorService.saveAll(show.cast)
                episodes = episodeService.saveAll(show.episodes)
            }
            .let { Mono.just(it) }
    }

    @Transactional(readOnly = true)
    fun search(searchRequest: ShowSearchRequest): Flux<ShowEntity> {
        logger().info("Finding shows with request: {}", searchRequest)
        return showRepository.findAll(ShowSpecification.from(searchRequest))
            .let { Flux.fromIterable(it) }
    }

    @Transactional
    fun markAsWatched(externalId: Long): Mono<ShowEntity> {
        logger().info("Marking {} show as watched", externalId)
        return showRepository.findByExternalId(externalId)
            .let { Mono.justOrEmpty(it) }
            .doOnNext {
                it.episodes
                    .map { episode -> episode.externalId }
                    .let { episodeService.markAsWatched(it) }
            }
    }

    @Transactional
    fun delete(externalId: Long): Mono<Unit> {
        logger().info("Deleting {} show", externalId)
        return showRepository.deleteByExternalId(externalId).let { Mono.just(it) }
    }

    @Transactional(readOnly = true)
    fun getRecommendations(): Flux<Show> {
        logger().info("Getting recommendations based on most frequent actors")
        return showEndpoint.getShowsByActorIds(actorService.findMostFrequentActorExternalIds())
    }
}