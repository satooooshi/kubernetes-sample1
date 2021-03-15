package com.creationline.k8sthinkit.sample1.article.controller.request;

import lombok.NonNull;
import lombok.Value;

/**
 * 新規記事の作成のリクエストBody
 */
@Value
@NonNull
public class ArticleCreationRequest {

    /** 記事タイトル */
    private final String title;

    /** 著者名 */
    private final String author;

    /** 記事本文 */
    private final String body;

}
