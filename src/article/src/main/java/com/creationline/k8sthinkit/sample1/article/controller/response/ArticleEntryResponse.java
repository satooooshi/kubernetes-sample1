package com.creationline.k8sthinkit.sample1.article.controller.response;

import lombok.NonNull;
import lombok.Value;

/**
 * 記事一覧の要素
 */
@Value
@NonNull
public class ArticleEntryResponse {

    /** 記事ID */
    private final Long id;

    /** 記事タイトル */
    private final String title;

    /** 著者 */
    private final String author;

}
