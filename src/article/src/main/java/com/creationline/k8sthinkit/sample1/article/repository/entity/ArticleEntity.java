package com.creationline.k8sthinkit.sample1.article.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Table("article")
public class ArticleEntity {
    @Id
    private final Long id;
    @NonNull
    private final String title;
    @NonNull
    private final String author;
    @NonNull
    private final String body;
}
