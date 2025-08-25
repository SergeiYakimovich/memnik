package com.memnik.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    @Query("SELECT e FROM #{#entityName} e WHERE e.used = false ORDER BY RANDOM() LIMIT 1")
    Optional<T> findRandomByUsedFalse();

    @Query("SELECT e FROM #{#entityName} e WHERE e.used = false AND " +
            "(e.language = :language OR e.language = 'ANY') ORDER BY RANDOM() LIMIT 1")
    Optional<T> findRandomByLanguageAndUsedFalse(@Param("language") String language);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE #{#entityName} e SET e.used = true WHERE e.id = :id")
    int setUsedTrue(@Param("id") Long id);

    default Optional<T> findRandom(String language, List<String> tagNames, String author) {
        Specification<T> byRandom = BaseSpecifications.orderByRandom();
        Specification<T> specification = BaseSpecifications
                .findByLanguageAndTagsAndAuthor(language, tagNames, author);
        specification = specification.and(byRandom);

        return findAll(specification, PageRequest.of(0, 1))
                .stream()
                .findFirst();
    }
}
