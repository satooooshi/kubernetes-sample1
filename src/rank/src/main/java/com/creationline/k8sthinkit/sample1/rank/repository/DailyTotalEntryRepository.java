package com.creationline.k8sthinkit.sample1.rank.repository;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.lang.NonNull;

import reactor.core.publisher.Flux;

/**
 * 日次延べアクセス順位を永続化するRepository
 * 
 * 実装はSpring Data R2DBCにより生成される
 */
public interface DailyTotalEntryRepository extends ReactiveCrudRepository<DailyTotalEntry, Long> {

    /**
     * 指定された日付のアクセス順位を取得するカスタムSQLクエリ
     * 
     * @param date アクセス順位を取得する日付
     * @return 指定された日付のアクセス順位
     */
    @Query( //
        "SELECT " + //
            "e.* " + //
        "FROM daily_total_entry AS e " + //
        "WHERE " + //
            "e.date = :date" //
    )
    Flux<DailyTotalEntry> findByDate(@NonNull final LocalDate date);

}
