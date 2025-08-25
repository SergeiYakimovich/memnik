package com.memnik.dao.base;

import com.memnik.common.constants.Languages;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.memnik.common.constants.Constants.ANY_AUTHOR;

public class BaseSpecifications {

    public static <T> Specification<T> findByLanguageAndTagsAndAuthor(String language, List<String> tagNames, String author) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            final Collection<Predicate> predicates = new ArrayList<>();

            if(!language.equals(Languages.ANY.name())) {
                predicates.add(cb.or(
                        cb.equal(root.get("language"), language),
                        cb.equal(root.get("language"), Languages.ANY.name())));
            }
            if(!author.equals(ANY_AUTHOR)) {
                predicates.add(cb.equal(root.get("author"), author));
            }
            if (tagNames != null && !tagNames.isEmpty()) {
                Subquery<Long> tagSubquery = query.subquery(Long.class);
                Root<T> jokeRoot = tagSubquery.correlate(root);
                Join<?, ?> tagsJoin = jokeRoot.join("tags");
                tagSubquery.select(cb.literal(1L))
                        .where(tagsJoin.get("name").in(tagNames));
                predicates.add(cb.exists(tagSubquery));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static <T> Specification<T> orderByRandom() {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(cb.function("RANDOM", Double.class)));
            return null; // Возвращаем null, так как это только для сортировки
        };
    }
}

