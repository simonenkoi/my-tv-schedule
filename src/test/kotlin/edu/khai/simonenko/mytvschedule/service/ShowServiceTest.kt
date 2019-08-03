package edu.khai.simonenko.mytvschedule.service

import assertk.assertThat
import assertk.assertions.isEmpty
import edu.khai.simonenko.mytvschedule.api.client.ShowEndpoint
import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.api.model.ShowStatus
import edu.khai.simonenko.mytvschedule.api.model.SortingDirection
import edu.khai.simonenko.mytvschedule.repository.EpisodeRepository
import edu.khai.simonenko.mytvschedule.repository.ShowRepository
import edu.khai.simonenko.mytvschedule.repository.model.EpisodeEntity
import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import edu.khai.simonenko.mytvschedule.testing.generator.generateRandomShow
import junit.framework.Assert.assertTrue
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@Rollback
class ShowServiceTest {

    companion object {
        const val FIRST_EPISODE_AIR_DATE_FIELD: String = "firstEpisodeAirDate"
        const val FIRST_UNWATCHED_EPISODE_AIR_DATE_FIELD: String = "firstUnwatchedEpisodeAirDate"
    }

    @Autowired
    lateinit var showService: ShowService

    @Autowired
    lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var episodeRepository: EpisodeRepository

    @MockBean
    lateinit var showEndpoint: ShowEndpoint

    @Before
    fun initialize() {
        Mockito.`when`(showEndpoint.getShowById(anyLong())).thenAnswer { Mono.just(generateRandomShow()) }
        (1L..20L).map { showService.save(it).block() }
    }

    @Test
    fun requestShouldReturnEntitiesWithoutWatchedShows() {
        val shows: List<ShowEntity> =
            showService.search(ShowSearchRequest(showStatus = ShowStatus.UNWATCHED)).collectList().block()!!


        val allUnwatched = shows.none { it.episodes.all { episode -> episode.watched } }

        assertTrue(allUnwatched)
    }

    @Test
    fun requestShouldSortByFirstEpisodeAirDateAscending() {
        val shows: List<ShowEntity> =
            showService.search(
                ShowSearchRequest(sortingField = FIRST_EPISODE_AIR_DATE_FIELD)
            ).collectList().block()!!


        val sorted = getFirstEpisodeAirDates(shows)
            .zipWithNext { s1, s2 -> s1 <= s2 }
            .all { it }

        assertTrue(sorted)
    }

    @Test
    fun requestShouldSortByFirstEpisodeAirDateDescending() {
        val shows: List<ShowEntity> =
            showService.search(
                ShowSearchRequest(
                    sortingField = FIRST_EPISODE_AIR_DATE_FIELD,
                    sortingDirection = SortingDirection.DESC
                )
            ).collectList().block()!!


        val sorted = getFirstEpisodeAirDates(shows)
            .zipWithNext { s1, s2 -> s1 >= s2 }
            .all { it }

        assertTrue(sorted)
    }

    @Test
    fun requestShouldSortByFirstUnwatchedEpisodeAirDateAscending() {
        val shows: List<ShowEntity> =
            showService.search(
                ShowSearchRequest(sortingField = FIRST_UNWATCHED_EPISODE_AIR_DATE_FIELD)
            ).collectList().block()!!


        val sorted = getFirstUnwatchedEpisodeAirDates(shows)
            .zipWithNext { s1, s2 -> s1 <= s2 }
            .all { it }

        assertTrue(sorted)
    }


    @Test
    fun requestShouldSortByFirstUnwatchedEpisodeAirDateDescending() {
        val shows: List<ShowEntity> =
            showService.search(
                ShowSearchRequest(
                    sortingField = FIRST_UNWATCHED_EPISODE_AIR_DATE_FIELD,
                    sortingDirection = SortingDirection.DESC
                )
            ).collectList().block()!!


        val sorted = getFirstUnwatchedEpisodeAirDates(shows)
            .zipWithNext { s1, s2 -> s1 >= s2 }
            .all { it }

        assertTrue(sorted)
    }

    @Test
    fun requestShouldMarkAllEpisodesOfShowAsWatched() {
        val show: ShowEntity = showService.search(ShowSearchRequest()).collectList().block()!!.first()

        showService.markAsWatched(show.externalId).block()

        val episodes = episodeRepository.findByExternalIdIn(show.episodeExternalIds())
        assertTrue(episodes.all { it.watched })
    }

    @Test
    fun requestShouldDeleteShow() {
        val show: ShowEntity = showService.search(ShowSearchRequest()).collectList().block()!!.first()

        showService.delete(show.externalId)

        assertNull(showRepository.findByExternalId(show.externalId))
        assertThat(episodeRepository.findByExternalIdIn(show.episodeExternalIds())).isEmpty()
    }

    private fun ShowEntity.episodeExternalIds(): List<Long> = episodes.map { it.externalId }

    private fun getFirstUnwatchedEpisodeAirDates(shows: List<ShowEntity>): List<LocalDate> {
        return getFirstEpisodeAirDates(shows) { !it.watched }
    }

    private fun getFirstEpisodeAirDates(
        shows: List<ShowEntity>,
        episodePredicate: (EpisodeEntity) -> Boolean = { true }
    ): List<LocalDate> {
        return shows.map {
            it.episodes.sortedBy { episode -> episode.airdate }.firstOrNull(episodePredicate)
        }
            .mapNotNull { it?.airdate }
    }

}
