package com.creationline.k8sthinkit.sample1.rank.service.article.entity;

import lombok.Value;

/**
 * Articleサービスから取得する記事一覧のエントリ
 */
@Value
public class ArticleEntry {
    private Long id;
    private String title;
    private String author;
}
