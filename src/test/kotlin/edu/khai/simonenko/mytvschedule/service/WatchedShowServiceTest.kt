package edu.khai.simonenko.mytvschedule.service

import edu.khai.simonenko.mytvschedule.api.client.ShowEndpoint
import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.api.model.ShowStatus
import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import edu.khai.simonenko.mytvschedule.testing.generator.generateRandomWatchedShow
import junit.framework.Assert.assertTrue
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

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
@Rollback
class WatchedShowServiceTest {

    @Autowired
    lateinit var showService: ShowService

    @MockBean
    lateinit var showEndpoint: ShowEndpoint

    @Before
    fun initialize() {
        Mockito.`when`(showEndpoint.getShowById(anyLong())).thenAnswer { Mono.just(generateRandomWatchedShow()) }
        (1L..20L).map { showService.save(it).block() }
    }

    @Test
    fun requestShouldReturnEntitiesWithoutWatchedShows() {
        val unwatchedShows: List<ShowEntity> = showService.search(
            ShowSearchRequest(showStatus = ShowStatus.UNWATCHED)
        ).collectList().block()!!

        assertTrue(unwatchedShows.isEmpty())
    }

}
