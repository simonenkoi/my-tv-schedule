package edu.khai.simonenko.mytvschedule.api.client

import edu.khai.simonenko.mytvschedule.api.model.Show
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ShowEndpoint {

    fun getShowById(id: Long): Mono<Show>

    fun getShowsByActorIds(ids: Flux<Long>): Flux<Show>
}