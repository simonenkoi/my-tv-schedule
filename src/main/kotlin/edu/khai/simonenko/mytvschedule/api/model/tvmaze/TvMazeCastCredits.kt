package edu.khai.simonenko.mytvschedule.api.model.tvmaze

import com.fasterxml.jackson.annotation.JsonProperty

data class TvMazeCastCredits(
    @JsonProperty("_embedded")
    val embedded: TvMazeCastCreditsEmbedded
)