package com.creationline.k8sthinkit.sample1.article.controller.response;

import lombok.NonNull;
import lombok.Value;

@Value
@NonNull
public class ArticleNotFoundResponse {
    private String message;
}
