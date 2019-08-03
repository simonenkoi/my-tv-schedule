package edu.khai.simonenko.mytvschedule.testing.generator

import edu.khai.simonenko.mytvschedule.api.model.Actor
import edu.khai.simonenko.mytvschedule.api.model.Episode
import edu.khai.simonenko.mytvschedule.api.model.Show
import org.apache.commons.lang3.RandomStringUtils
import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

fun generateRandomWatchedShow(): Show = generateRandomShow(watched = true)

fun generateRandomShow(watched: Boolean = randomBoolean()): Show {
    return Show(
        id = randomLong(),
        name = randomString(),
        image = randomString(),
        cast = generateRandomCast(),
        episodes = generateRandomEpisodes(watched)
    )
}

fun generateRandomCast(): List<Actor> = (1..5).map { generateRandomActor() }

fun generateRandomActor(): Actor {
    return Actor(
        id = randomLong(),
        name = randomString(),
        image = randomString()
    )
}

fun generateRandomEpisodes(watched: Boolean = randomBoolean()): List<Episode> =
    (1..15).map { generateRandomEpisode(watched) }

fun generateRandomEpisode(watched: Boolean = randomBoolean()): Episode {
    return Episode(
        id = randomLong(),
        name = randomString(),
        season = randomInt(),
        number = randomInt(),
        airdate = randomLocalDate(),
        watched = watched
    )
}

private fun randomLong(): Long = ThreadLocalRandom.current().nextLong(100_000)

private fun randomInt(): Int = ThreadLocalRandom.current().nextInt(100_000)

private fun randomString(): String = RandomStringUtils.random(10)

private fun randomBoolean(): Boolean = ThreadLocalRandom.current().nextBoolean()

private fun randomLocalDate(): LocalDate {
    val minDay = LocalDate.of(1970, 1, 1).toEpochDay()
    val maxDay = LocalDate.now().toEpochDay()
    val randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)
    return LocalDate.ofEpochDay(randomDay)
}
