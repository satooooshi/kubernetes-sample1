package com.creationline.k8sthinkit.sample1.accesscount.repository;

import java.time.OffsetDateTime;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.Accesscount;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.lang.NonNull;

import reactor.core.publisher.Flux;

/**
 * アクセスカウントエンティティを永続化するRepositoryクラス
 * 
 * 実装はSpring Data R2DBCにより生成される
 */
public interface AccesscountRepository extends ReactiveSortingRepository<Accesscount, Long> {

    /**
     * 指定された記事ID/期間のアクセスカウントを取得するカスタムSQLクエリ
     * 
     * @param articleId アクセスカウントを取得する記事ID
     * @param fromIncluding 取得する期間の開始日時(指定された日時を期間に含む)
     * @param toExcluding 取得する期間の終了日時(指定された日時を機関に含まない)
     * @return 取得したアクセスカウント
     */
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
