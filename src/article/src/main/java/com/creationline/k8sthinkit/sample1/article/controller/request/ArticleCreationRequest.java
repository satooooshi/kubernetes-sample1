package com.creationline.k8sthinkit.sample1.article.controller.request;

import lombok.NonNull;
import lombok.Value;

@Value
@NonNull
public class ArticleCreationRequest {
    private final String title;
    private final String author;
    private final String body;
}
