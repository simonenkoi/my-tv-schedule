package edu.khai.simonenko.mytvschedule.api.controller

import edu.khai.simonenko.mytvschedule.api.model.Episode
import edu.khai.simonenko.mytvschedule.service.transformer.EpisodeTransformer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/episodes")
class EpisodeController(val episodeTransformer: EpisodeTransformer) {

    @GetMapping("/first-unwatched")
    fun findFirstUnwatchedEpisodeOfEachShow(): ResponseEntity<Flux<Episode>> {
        return ResponseEntity.ok(episodeTransformer.findFirstUnwatchedOfEachShow())
    }

    @PatchMapping("/{id}/mark-as-watched")
    fun markEpisodeasWatched(@PathVariable("id") id: Long): ResponseEntity<Mono<Episode>> {
        return ResponseEntity.ok(episodeTransformer.markAsWatched(id))
    }

}