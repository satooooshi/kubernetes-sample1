package com.creationline.k8sthinkit.sample1.accesscount.controller.response;

import java.time.OffsetDateTime;

import lombok.NonNull;
import lombok.Value;

/**
 * アクセス数統計を表すレスポンスBody
 */
@Value
public class AccessStatsResponse {

    /** 記事ID */
    @NonNull
    private Long articleId;
    /** 延べアクセス数 */
    @NonNull
    private Long totalAccess;
    /** ユニークアクセス数 */
    @NonNull
    private Long uniqueAccess;
    /** 集計期間開始日時(この日時を含む) */
    @NonNull
    private OffsetDateTime from;
    /** 集計期間終了日時(この日時を含まない) */
    @NonNull
    private OffsetDateTime to;

}
