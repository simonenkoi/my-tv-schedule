package edu.khai.simonenko.mytvschedule.api.client

import edu.khai.simonenko.mytvschedule.api.model.Show
import edu.khai.simonenko.mytvschedule.api.model.tvmaze.TvMazeCastCredits
import edu.khai.simonenko.mytvschedule.api.model.tvmaze.TvMazeShow
import edu.khai.simonenko.mytvschedule.api.model.tvmaze.toShow
import edu.khai.simonenko.mytvschedule.configuration.annotation.Endpoint
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Endpoint
class TvMazeEndpoint(val tvMazeWebClient: WebClient) : ShowEndpoint {
    override fun getShowById(id: Long): Mono<Show> {
        return tvMazeWebClient.get()
            .uri("shows/{id}?embed[]=episodes&embed[]=cast", id)
            .retrieve()
            .bodyToMono(TvMazeShow::class.java)
            .map(TvMazeShow::toShow)
            .log()
    }

    override fun getShowsByActorIds(ids: Flux<Long>): Flux<Show> {
        return ids.map {
            tvMazeWebClient.get()
                .uri("people/{id}/castcredits?embed=show", it)
                .retrieve()
                .bodyToMono(Array<TvMazeCastCredits>::class.java)
                .map { (credits) -> credits }
                .map { (embedded) -> embedded }
                .map { (show) -> show }
                .map(TvMazeShow::toShow)
                .log()
        }
            .let { Flux.concat(it) }
            .distinct()
    }
}