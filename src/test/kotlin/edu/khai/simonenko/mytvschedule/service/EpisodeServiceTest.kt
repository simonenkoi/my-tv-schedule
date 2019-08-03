package edu.khai.simonenko.mytvschedule.service

import edu.khai.simonenko.mytvschedule.api.client.ShowEndpoint
import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.repository.EpisodeRepository
import edu.khai.simonenko.mytvschedule.repository.model.EpisodeEntity
import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import edu.khai.simonenko.mytvschedule.testing.generator.generateRandomShow
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@Rollback
class EpisodeServiceTest {

    @Autowired
    lateinit var showService: ShowService

    @Autowired
    lateinit var episodeService: EpisodeService

    @Autowired
    lateinit var episodeRepository: EpisodeRepository

    @MockBean
    lateinit var showEndpoint: ShowEndpoint

    @Before
    fun initialize() {
        Mockito.`when`(showEndpoint.getShowById(ArgumentMatchers.anyLong())).thenAnswer {
            Mono.just(
                generateRandomShow()
            )
        }
        (1L..20L).map { showService.save(it).block() }
    }

    @Test
    fun requestShouldMarkEpisodeAsWatched() {
        val shows: List<ShowEntity> = showService.search(ShowSearchRequest()).collectList().block()!!
        val unwatchedEpisode = shows.flatMap { it.episodes }.first { !it.watched }

        episodeService.markAsWatched(unwatchedEpisode.externalId)

        val watchedEpisode = episodeRepository.findByExternalId(unwatchedEpisode.externalId)
        assertTrue(watchedEpisode!!.watched)
    }

    @Test
    fun requestShouldReturnUnwatchedEpisodesOfEachShow() {
        val episodes: List<EpisodeEntity> =
            episodeService.findFirstUnwatchedOfEachShow().collectList().block()!!

        val expectedEpisodes: List<EpisodeEntity> = getFirstEpisodes(showService.search(ShowSearchRequest()))
        assertTrue(expectedEpisodes.containsAll(episodes))
    }

    private fun getFirstEpisodes(shows: Flux<ShowEntity>): List<EpisodeEntity> {
        return shows.collectList().block()!!
            .mapNotNull {
                it.episodes.sortedBy { episode -> episode.airdate }.firstOrNull { episode -> !episode.watched }
            }
    }
}