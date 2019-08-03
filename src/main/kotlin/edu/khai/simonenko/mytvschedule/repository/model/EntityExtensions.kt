package edu.khai.simonenko.mytvschedule.repository.model

import edu.khai.simonenko.mytvschedule.api.model.Actor
import edu.khai.simonenko.mytvschedule.api.model.Episode
import edu.khai.simonenko.mytvschedule.api.model.Show

fun ShowEntity.toShow(): Show {
    return Show(
        id = this.externalId,
        name = this.name,
        image = this.image
    )
}

fun ShowEntity.toShowEager(fetchCast: Boolean = false, fetchEpisodes: Boolean = false): Show {
    return this.toShow()
        .also {
            if (fetchCast) {
                it.cast = this.cast.toActors()
            }
            if (fetchEpisodes) {
                it.episodes = this.episodes.toEpisodes()
            }
        }
}

fun Collection<ActorEntity>.toActors(): List<Actor> = this.map { it.toActor() }

fun ActorEntity.toActor(): Actor {
    return Actor(
        id = this.externalId,
        name = this.name,
        image = this.image
    )
}

fun Collection<EpisodeEntity>.toEpisodes(): List<Episode> = this.map { it.toEpisode() }

fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
        id = this.externalId,
        name = this.name,
        season = this.season,
        number = this.number,
        airdate = this.airdate,
        watched = this.watched
    )
}

fun EpisodeEntity.toEpisodeEager(fetchShow: Boolean = false): Episode {
    return this.toEpisode()
        .also {
            if (fetchShow) {
                it.show = this.show?.toShow()
            }
        }

}