package com.creationline.k8sthinkit.sample1.accesscount.controller.request;

import java.time.OffsetDateTime;

import lombok.NonNull;
import lombok.Value;

/**
 * アクセス記録作成のリクエストBody
 */
@Value
public class AccessRecordRequest {

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
