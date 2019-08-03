package edu.khai.simonenko.mytvschedule.api.model.tvmaze

data class TvMazeShowEmbedded(
    val cast: List<TvMazeCastMember> = emptyList(),
    val episodes: List<TvMazeEpisode> = emptyList()
)