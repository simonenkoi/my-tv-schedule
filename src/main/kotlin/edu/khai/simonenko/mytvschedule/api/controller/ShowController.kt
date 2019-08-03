package edu.khai.simonenko.mytvschedule.api.controller

import edu.khai.simonenko.mytvschedule.api.model.Show
import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.service.transformer.ShowTransformer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/shows")
class ShowController(val showTransformer: ShowTransformer) {

    @GetMapping
    fun getShowsInSchedule(searchRequest: ShowSearchRequest = ShowSearchRequest()): ResponseEntity<Flux<Show>> {
        return ResponseEntity.ok(showTransformer.search(searchRequest))
    }

    @GetMapping("/recommendations")
    fun getRecommendations(): ResponseEntity<Flux<Show>> {
        return ResponseEntity.ok(showTransformer.getRecommendations())
    }

    @PostMapping("/{id}")
    fun addShowToSchedule(@PathVariable("id") id: Long): ResponseEntity<Mono<Show>> {
        return ResponseEntity.ok(showTransformer.save(id))
    }

    @PatchMapping("/{id}/mark-as-watched")
    fun markShowAsWatched(@PathVariable("id") id: Long): ResponseEntity<Mono<Show>> {
        return ResponseEntity.ok(showTransformer.markAsWatched(id))
    }

    @DeleteMapping("/{id}")
    fun deleteShowFromSchedule(@PathVariable("id") id: Long): ResponseEntity<Mono<Unit>> {
        return ResponseEntity.ok(showTransformer.delete(id))
    }
}