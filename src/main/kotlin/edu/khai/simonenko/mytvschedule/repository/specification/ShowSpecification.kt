package edu.khai.simonenko.mytvschedule.repository.specification

import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.api.model.ShowStatus.ALL
import edu.khai.simonenko.mytvschedule.api.model.ShowStatus.UNWATCHED
import edu.khai.simonenko.mytvschedule.api.model.SortingDirection.ASC
import edu.khai.simonenko.mytvschedule.repository.model.EpisodeEntity
import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class ShowSpecification private constructor(val searchRequest: ShowSearchRequest) : Specification<ShowEntity> {

    companion object {
        fun from(searchRequest: ShowSearchRequest) = ShowSpecification(searchRequest)
    }

    //TODO isimonenko: find a way to generate metamodel for kotlin classes instead of passing string parameters
    fun buildStatus(): Specification<ShowEntity>? {
        return Specification { root, query, cb ->
            when (searchRequest.showStatus) {
                ALL -> null
                UNWATCHED -> {
                    query.distinct(true)
                    cb.isFalse(root.join<ShowEntity, EpisodeEntity>("episodes").get("watched"))
                }
            }
        }
    }

    fun buildSort(): Specification<ShowEntity>? {
        return Specification { root, query, cb ->
            searchRequest.sortingField?.let {
                val sortingPath: Path<Any> = root.get(it)
                query.orderBy(
                    if (searchRequest.sortingDirection == ASC)
                        cb.asc(sortingPath)
                    else cb.desc(sortingPath)
                )
            }
            null
        }
    }

    override fun toPredicate(root: Root<ShowEntity>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate? {
        return Specification.where<ShowEntity>(buildStatus()!!)
            .and(buildSort()!!)
            .toPredicate(root, query, cb)
    }
}