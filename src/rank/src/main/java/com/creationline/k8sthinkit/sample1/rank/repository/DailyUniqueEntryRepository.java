package com.creationline.k8sthinkit.sample1.rank.repository;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyUniqueEntry;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import lombok.NonNull;
import reactor.core.publisher.Flux;

/**
 * 日次アクセスユーザ順位を永続化するRepository
 * 
 * 実装はSpring Data R2DBCにより生成される
 */
public interface DailyUniqueEntryRepository extends ReactiveSortingRepository<DailyUniqueEntry, Long> {

    /**
     * 指定された日付のアクセス順位を取得するカスタムSQLクエリ
     * 
     * @param date アクセス順位を取得する日付
     * @return 指定された日付のアクセス順位
     */
    @Query( //
        "SELECT " + //
            "e.* " + //
        "FROM daily_unique_entry e " + //
        "WHERE " + //
            "e.date = :date" //
    )
    Flux<DailyUniqueEntry> findByDate(@NonNull final LocalDate date);

}
