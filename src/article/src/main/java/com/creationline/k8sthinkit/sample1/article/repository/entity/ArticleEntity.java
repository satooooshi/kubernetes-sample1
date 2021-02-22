package com.creationline.k8sthinkit.sample1.article.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleEntity {
    private final Long id;
    private final String title;
    private final String author;
    private final String body;
}
