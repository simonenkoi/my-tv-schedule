package edu.khai.simonenko.mytvschedule.api.model

data class ShowSearchRequest(
    val showStatus: ShowStatus = ShowStatus.ALL,
    val sortingField: String? = null,
    val sortingDirection: SortingDirection = SortingDirection.ASC
)