package com.creationline.k8sthinkit.sample1.accesscount.repository;

import java.time.OffsetDateTime;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.Accesscount;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.lang.NonNull;

import reactor.core.publisher.Flux;

public interface AccesscountRepository extends ReactiveSortingRepository<Accesscount, Long> {

    @NonNull
    @Query(value = //
        "SELECT " + //
            "a.* " + //
        "FROM accesscount a " + //
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
