package com.creationline.k8sthinkit.sample1.rank.repository.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.NonNull;
import lombok.Value;

/**
 * 日次延べアクセス順位
 */
@Value
@Table("daily_total_entry")
public class DailyTotalEntry {

    /** 人工代理キー */
    @Id
    private Long id;

    /** 日付 */
    @NonNull
    private LocalDate date;

    /** 順位 */
    @NonNull
    private Integer rank;

    /** 記事ID */
    @NonNull
    @Column("article_id")
    private Long articleId;

    /** 延べアクセス数 */
    @NonNull
    @Column("total_access")
    private Long totalAccess;

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
    public DailyTotalEntry withId(@NonNull final Long id) {
        return new DailyTotalEntry( //
            id, //
            this.date, //
            this.rank, //
            this.articleId, //
            this.totalAccess, //
            this.title, //
            this.author //
        );
    }

}
