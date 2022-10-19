package com.tkachev.adboard.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AppSpecification<T> implements Specification<T> {

    private List<Filter> list;

    public AppSpecification() {
        list = new ArrayList<>();
    }

    public void add(Filter criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        //add filter to predicate
        for (Filter filter : list) {
            if (filter.getOperator().equals(QueryOperator.GREATER_THAN)) {
                predicates.add(builder.greaterThan(
                        root.get(filter.getField()), filter.getValue().toString()));
            } else if (filter.getOperator().equals(QueryOperator.LESS_THAN)) {
                predicates.add(builder.lessThan(
                        root.get(filter.getField()), filter.getValue().toString()));
            } else if (filter.getOperator().equals(QueryOperator.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(filter.getField()), filter.getValue().toString()));
            } else if (filter.getOperator().equals(QueryOperator.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(filter.getField()), filter.getValue().toString()));
            } else if (filter.getOperator().equals(QueryOperator.NOT_EQUAL)) {
                predicates.add(builder.notEqual(
                        root.get(filter.getField()), filter.getValue()));
            } else if (filter.getOperator().equals(QueryOperator.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(filter.getField()), filter.getValue()));
            } else if (filter.getOperator().equals(QueryOperator.MATCH)) {
                predicates.add(builder.like(
                        builder.lower(root.get(filter.getField())),
                        "%" + filter.getValue().toString().toLowerCase() + "%"));
            } else if (filter.getOperator().equals(QueryOperator.MATCH_END)) {
                predicates.add(builder.like(
                        builder.lower(root.get(filter.getField())),
                        filter.getValue().toString().toLowerCase() + "%"));
            } else if (filter.getOperator().equals(QueryOperator.MATCH_START)) {
                predicates.add(builder.like(
                        builder.lower(root.get(filter.getField())),
                        "%" + filter.getValue().toString().toLowerCase()));
            } else if (filter.getOperator().equals(QueryOperator.IN)) {
                predicates.add(builder.in(root.get(filter.getField())).value(filter.getValue()));
            } else if (filter.getOperator().equals(QueryOperator.NOT_IN)) {
                predicates.add(builder.not(root.get(filter.getField())).in(filter.getValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
