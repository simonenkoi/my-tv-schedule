package edu.khai.simonenko.mytvschedule.service

import edu.khai.simonenko.mytvschedule.api.model.Actor
import edu.khai.simonenko.mytvschedule.common.Logging
import edu.khai.simonenko.mytvschedule.common.logger
import edu.khai.simonenko.mytvschedule.repository.ActorRepository
import edu.khai.simonenko.mytvschedule.repository.model.ActorEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux

@Service
open class ActorService(val actorRepository: ActorRepository) : Logging {

    @Transactional
    fun saveAll(actors: Collection<Actor>?): List<ActorEntity> {
        return actors?.distinctBy { it.id }
            ?.also { logger().info("Saving {} actors", it.size) }
            ?.map { createOrUpdate(it) }
            ?.also { actorRepository.saveAll(it) }
            .orEmpty()
    }

    private fun createOrUpdate(actor: Actor): ActorEntity {
        return (actorRepository.findByExternalId(actor.id) ?: ActorEntity(externalId = actor.id))
            .apply { this.update(actor) }
    }

    @Transactional(readOnly = true)
    fun findMostFrequentActorExternalIds(): Flux<Long> {
        logger().info("Finding external ids of most frequent actors")
        return actorRepository.findMostFrequentActorExternalIds()
            .let { Flux.fromIterable(it) }
    }

    private fun ActorEntity.update(actor: Actor): ActorEntity {
        return this.apply {
            name = actor.name
            image = actor.image
        }
    }
}