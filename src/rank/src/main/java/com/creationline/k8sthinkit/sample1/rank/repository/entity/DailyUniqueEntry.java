package com.creationline.k8sthinkit.sample1.rank.repository.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.NonNull;
import lombok.Value;

/**
 * 日次アクセスユーザ順位
 */
@Value
@Table("daily_unique_entry")
public class DailyUniqueEntry {

    /** 人工代理キー */
    @Id
    private Long id;

    /** 日次 */
    @NonNull
    private LocalDate date;

    /** 順位 */
    @NonNull
    private Integer rank;

    /** 記事ID */
    @NonNull
    @Column("article_id")
    private Long articleId;

    /** アクセスユーザ数 */
    @NonNull
    @Column("unique_access")
    private Long uniqueAccess;

    /** タイトル */
    @NonNull
    private String title;

    /** 著者 */
    @NonNull
    private String author;

    /**
     * ID付与.
     * 
     * このインスタンスのフィールドの値を使ってIDのみ新しい別のインスタンスを作成する
     * 
     * @param id 付与するID
     * @return 指定されたIDが付与された新しいインスタンス
     */
    public DailyUniqueEntry withId(@NonNull final Long id) {
        return new DailyUniqueEntry(
            id, //
            this.date, //
            this.rank, //
            this.articleId, //
            this.uniqueAccess, //
            this.title, //
            this.author //
        );
    }

}
