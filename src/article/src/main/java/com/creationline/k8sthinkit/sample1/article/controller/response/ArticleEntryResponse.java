package com.creationline.k8sthinkit.sample1.article.controller.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ArticleEntryResponse {
    private final String title;
    private final String author;
}
