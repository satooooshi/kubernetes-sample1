package com.creationline.k8sthinkit.sample1.accesscount.repository;

import java.time.OffsetDateTime;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.Accesscount;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface AccesscountRepository extends ReactiveSortingRepository<Accesscount, Long> {

    @NonNull
    @Query(value = //
        "SELECT " + //
            "a.id AS id, " + //
            "a.article_id AS article_id, " + //
            "a.uid AS uid, " + //
            "a.access_at AS access_at " + //
        "FROM Accesscount a " + //
        "WHERE " + //
            "a.article_id = :articleId " + //
            "AND :fromIncluding <= a.access_at " + //
            "AND a.access_at < :toExcluding" //
    )
    Flux<Accesscount> findByArticleIdAndAccessAtInTerm( //
        @NonNull Long articleId, //
        @NonNull OffsetDateTime fromIncluding, //
        @NonNull OffsetDateTime toExcluding //
    );

}
