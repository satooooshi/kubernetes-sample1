package com.creationline.k8sthinkit.sample1.accesscount.repository.entity;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.NonNull;
import lombok.Value;

/**
 * アクセスカウントエンティティクラス
 * 
 * 1エンティティがユーザによる1回のアクセスに対応する
 */
@Value
@Table("accesscount")
public class Accesscount {

    /** ID (人工代理キー) */
    @Id
    private Long id;

    /** 記事ID */
    @NonNull
    @Column("article_id")
    private Long articleId;

    /** ユーザを識別する文字列 */
    @NonNull
    private String uid;

    /** アクセス日時 */
    @NonNull
    @Column("access_at")
    private OffsetDateTime accessAt;

}
