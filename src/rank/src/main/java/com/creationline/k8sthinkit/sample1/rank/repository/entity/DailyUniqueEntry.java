package com.creationline.k8sthinkit.sample1.rank.repository.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.NonNull;
import lombok.Value;

@Value
@Table("daily_unique_entry")
public class DailyUniqueEntry {
    @Id
    private Long id;
    @NonNull
    private LocalDate date;
    @NonNull
    private Integer rank;
    @NonNull
    @Column("article_id")
    private Long articleId;
    @NonNull
    @Column("unique_access")
    private Long uniqueAccess;

    public DailyUniqueEntry withId(@NonNull final Long id) {
        return new DailyUniqueEntry(
            id, //
            this.date, //
            this.rank, //
            this.articleId, //
            this.uniqueAccess //
        );
    }

}
