package edu.khai.simonenko.mytvschedule.api.model.tvmaze

import edu.khai.simonenko.mytvschedule.api.model.Actor
import edu.khai.simonenko.mytvschedule.api.model.Episode
import edu.khai.simonenko.mytvschedule.api.model.Show

fun TvMazeShow.toShow(): Show = Show(
    id = this.id,
    name = this.name,
    image = this.image?.original,
    cast = this.embedded?.cast.toActors(),
    episodes = this.embedded?.episodes.toEpisodes()
)

fun TvMazeCastMember.toActor(): Actor = Actor(
    id = this.person.id,
    name = this.person.name,
    image = this.person.image?.medium
)

fun Collection<TvMazeCastMember>?.toActors(): List<Actor>? = this?.map { it.toActor() }

fun TvMazeEpisode.toEpisode(): Episode = Episode(
    id = this.id,
    name = this.name,
    season = this.season,
    number = this.number,
    airdate = this.airdate
)

fun Collection<TvMazeEpisode>?.toEpisodes(): List<Episode>? = this?.map { it.toEpisode() }