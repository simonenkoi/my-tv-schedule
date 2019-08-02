package edu.khai.simonenko.mytvschedule.service

import edu.khai.simonenko.mytvschedule.api.client.MovieEndpoint
import edu.khai.simonenko.mytvschedule.repository.MovieRepository
import edu.khai.simonenko.mytvschedule.repository.model.MovieEntity
import edu.khai.simonenko.mytvschedule.repository.model.fromMovie
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
open class MovieService(
    val movieRepository: MovieRepository,
    val movieEndpoint: MovieEndpoint
) {

    @Transactional
    fun saveMovie(id: Long): Mono<MovieEntity> {
        return movieEndpoint.getMovieById(id)
            .map { MovieEntity.fromMovie(it) }
            .map { movieRepository.save(it) }
    }
}