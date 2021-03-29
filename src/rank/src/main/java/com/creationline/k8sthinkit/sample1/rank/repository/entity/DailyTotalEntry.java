package com.creationline.k8sthinkit.sample1.rank.repository.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.NonNull;
import lombok.Value;

@Value
@Table("daily_total_entry")
public class DailyTotalEntry {
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
    @Column("total_access")
    private Long totalAccess;

    public DailyTotalEntry withId(@NonNull final Long id) {
        return new DailyTotalEntry( //
            id, //
            this.date, //
            this.rank, //
            this.articleId, //
            this.totalAccess //
        );
    }

}
