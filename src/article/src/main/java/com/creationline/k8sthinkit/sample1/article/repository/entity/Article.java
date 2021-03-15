package com.creationline.k8sthinkit.sample1.article.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NonNull;

/**
 * 記事
 */
@Data
@Table("article")
public class Article {

    /** 記事ID */
    @Id
    private final Long id;

    /** 記事タイトル */
    @NonNull
    private final String title;

    /** 著者 */
    @NonNull
    private final String author;

    /** 記事本文 */
    @NonNull
    private final String body;

}
