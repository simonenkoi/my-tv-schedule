package edu.khai.simonenko.mytvschedule.service.transformer

import edu.khai.simonenko.mytvschedule.api.model.Show
import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.configuration.annotation.Transformer
import edu.khai.simonenko.mytvschedule.repository.model.toShow
import edu.khai.simonenko.mytvschedule.repository.model.toShowEager
import edu.khai.simonenko.mytvschedule.service.ShowService
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Transformer
open class ShowTransformer(val showService: ShowService) {

    @Transactional
    fun save(id: Long): Mono<Show> {
        return showService.save(id).map { it.toShow() }
    }

    @Transactional
    fun delete(id: Long): Mono<Unit> {
        return showService.delete(id)
    }

    @Transactional(readOnly = true)
    fun search(searchRequest: ShowSearchRequest): Flux<Show> {
        val search = showService.search(searchRequest)
        return search.map { it.toShowEager(fetchCast = true) }
    }

    @Transactional
    fun markAsWatched(id: Long): Mono<Show> {
        return showService.markAsWatched(id).map { it.toShow() }
    }

    @Transactional(readOnly = true)
    fun getRecommendations(): Flux<Show> {
        return showService.getRecommendations()
    }

}