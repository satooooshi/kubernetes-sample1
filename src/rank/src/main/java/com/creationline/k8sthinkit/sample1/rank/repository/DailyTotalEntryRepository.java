package com.creationline.k8sthinkit.sample1.rank.repository;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyTotalEntry;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.lang.NonNull;

import reactor.core.publisher.Flux;

public interface DailyTotalEntryRepository extends ReactiveCrudRepository<DailyTotalEntry, Long> {

    @Query( //
        "SELECT " + //
            "e.* " + //
        "FROM daily_total_entry AS e " + //
        "WHERE " + //
            "e.date = :date" //
    )
    Flux<DailyTotalEntry> findByDate(@NonNull final LocalDate date);

}
