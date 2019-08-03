package edu.khai.simonenko.mytvschedule.repository.model

import edu.khai.simonenko.mytvschedule.api.model.Actor
import edu.khai.simonenko.mytvschedule.api.model.Episode
import edu.khai.simonenko.mytvschedule.api.model.Show

fun ShowEntity.Companion.fromShow(show: Show): ShowEntity = ShowEntity(
    externalId = show.id,
    name = show.name,
    image = show.image,
    cast = ActorEntity.fromActors(show.cast),
    episodes = EpisodeEntity.fromEpisodes(show.episodes)
)

fun ActorEntity.Companion.fromActors(actors: Collection<Actor>?): MutableList<ActorEntity> =
    actors?.map { fromActor(it) }.orEmpty().toMutableList()

fun ActorEntity.Companion.fromActor(actor: Actor): ActorEntity = ActorEntity(
    externalId = actor.id,
    name = actor.name,
    image = actor.image
)

fun EpisodeEntity.Companion.fromEpisodes(episodes: Collection<Episode>?): MutableList<EpisodeEntity> =
    episodes?.map { fromEpisode(it) }.orEmpty().toMutableList()

fun EpisodeEntity.Companion.fromEpisode(episode: Episode): EpisodeEntity = EpisodeEntity(
    externalId = episode.id,
    name = episode.name,
    season = episode.season,
    number = episode.number,
    airdate = episode.airdate,
    watched = episode.watched
)