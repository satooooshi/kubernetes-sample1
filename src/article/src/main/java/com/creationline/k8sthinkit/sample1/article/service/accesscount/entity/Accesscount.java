package com.creationline.k8sthinkit.sample1.article.service.accesscount.entity;

import java.time.OffsetDateTime;

import lombok.NonNull;
import lombok.Value;

@Value
public class Accesscount {

    /** 記事ID */
    @NonNull
    private Long articleId;

    /** アクセスユーザID */
    @NonNull
    private String uid;

    /** アクセス日時 */
    @NonNull
    private OffsetDateTime accessAt;

}
