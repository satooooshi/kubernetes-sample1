package com.creationline.k8sthinkit.sample1.rank.service.accesscount.entity;

import java.time.OffsetDateTime;

import lombok.NonNull;
import lombok.Value;

/**
 * アクセス統計
 */
@Value
public class AccessStats {

    /** 記事ID */
    @NonNull
    private Long articleId;

    /** 延べアクセス数 */
    @NonNull
    private Long totalAccess;

    /** アクセスユーザ数 */
    @NonNull
    private Long uniqueAccess;

    /** 集計期間の開始日時(この日時を期間に含む) */
    @NonNull
    private OffsetDateTime from;

    /** 集計期間の終了日時(この日時を期間に含まない) */
    @NonNull
    private OffsetDateTime to;

}
