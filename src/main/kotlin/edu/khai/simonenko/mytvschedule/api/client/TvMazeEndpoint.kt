package edu.khai.simonenko.mytvschedule.api.client

import edu.khai.simonenko.mytvschedule.api.model.Movie
import edu.khai.simonenko.mytvschedule.api.model.tvmaze.TvMazeShow
import edu.khai.simonenko.mytvschedule.api.model.tvmaze.toMovie
import edu.khai.simonenko.mytvschedule.configuration.annotation.Endpoint
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Endpoint
class TvMazeEndpoint(val tvMazeWebClient: WebClient) : MovieEndpoint {

    override fun getMovieById(id: Long): Mono<Movie> {
        return tvMazeWebClient.get()
            .uri("shows/{id}?embed[]=episodes&embed[]=cast", id)
            .retrieve()
            .bodyToMono(TvMazeShow::class.java)
            .map(TvMazeShow::toMovie)
    }
}