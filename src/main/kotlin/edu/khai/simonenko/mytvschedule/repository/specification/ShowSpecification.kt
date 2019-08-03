package edu.khai.simonenko.mytvschedule.repository.specification

import edu.khai.simonenko.mytvschedule.api.model.ShowSearchRequest
import edu.khai.simonenko.mytvschedule.repository.model.ShowEntity
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class ShowSpecification(val showSearchRequest: ShowSearchRequest) : Specification<ShowEntity> {
    override fun toPredicate(
        root: Root<ShowEntity>,
        query: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}