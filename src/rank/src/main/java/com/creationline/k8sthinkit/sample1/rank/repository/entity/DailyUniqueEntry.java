package com.creationline.k8sthinkit.sample1.rank.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.NonNull;
import lombok.Value;

@Value
@Table("daily_unique_entry")
public class DailyUniqueEntry {
    @Id
    @NonNull
    private DailyEntryId entryId;
    @NonNull
    @Column("article_id")
    private Long articleId;
    @NonNull
    @Column("unique_access")
    private Long uniqueAccess;
}
