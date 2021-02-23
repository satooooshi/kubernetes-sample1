package com.creationline.k8sthinkit.sample1.article.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ArticleDraft {
    @NonNull
    private final String title;
    @NonNull
    private final String author;
    @NonNull
    private final String body;
}
