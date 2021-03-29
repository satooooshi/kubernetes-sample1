package com.creationline.k8sthinkit.sample1.rank.repository;

import java.time.LocalDate;

import com.creationline.k8sthinkit.sample1.rank.repository.entity.DailyUniqueEntry;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import lombok.NonNull;
import reactor.core.publisher.Flux;

public interface DailyUniqueEntryRepository extends ReactiveSortingRepository<DailyUniqueEntry, Long> {

    @Query( //
        "SELECT " + //
            "e.* " + //
        "FROM daily_unique_entry e " + //
        "WHERE " + //
            "e.date = :date" //
    )
    Flux<DailyUniqueEntry> findByDate(@NonNull final LocalDate date);

}
