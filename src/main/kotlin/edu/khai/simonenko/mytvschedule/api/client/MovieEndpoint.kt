package edu.khai.simonenko.mytvschedule.api.client

import edu.khai.simonenko.mytvschedule.api.model.Movie
import reactor.core.publisher.Mono

interface MovieEndpoint {

    fun getMovieById(id: Long): Mono<Movie>
}