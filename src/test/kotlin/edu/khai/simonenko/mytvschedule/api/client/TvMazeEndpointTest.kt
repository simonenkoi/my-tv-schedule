package edu.khai.simonenko.mytvschedule.api.client

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import edu.khai.simonenko.mytvschedule.api.model.Movie
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClientResponseException

@RunWith(SpringRunner::class)
@SpringBootTest
class TvMazeEndpointTest {

    @Autowired
    lateinit var tvMazeClient: TvMazeEndpoint

    @Test
    fun requestShouldReturnCorrectMovie() {
        val movie: Movie? = tvMazeClient.getMovieById(10L).block()

        assertThat(movie?.id).isEqualTo(10L)
        assertThat(movie?.name).isNotNull().isEqualTo("Grimm")
        assertThat(movie?.image).isNotNull()
        assertThat(movie?.cast).isNotNull().isNotEmpty()
        assertThat(movie?.episodes).isNotNull().isNotEmpty()
    }

    @Test
    fun incorrectRequestShouldThrowBusinessException() {
        assertThat { tvMazeClient.getMovieById(-10).block() }
            .isFailure()
            .isInstanceOf(WebClientResponseException::class)
    }

}
