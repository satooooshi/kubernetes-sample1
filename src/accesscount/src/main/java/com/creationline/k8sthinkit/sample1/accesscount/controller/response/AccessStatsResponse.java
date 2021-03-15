package com.creationline.k8sthinkit.sample1.accesscount.controller.response;

import java.time.OffsetDateTime;

import lombok.NonNull;
import lombok.Value;

@Value
public class AccessStatsResponse {

    @NonNull
    private Long articleId;
    @NonNull
    private Long totalAccess;
    @NonNull
    private Long uniqueAccess;
    @NonNull
    private OffsetDateTime from;
    @NonNull
    private OffsetDateTime to;

}
