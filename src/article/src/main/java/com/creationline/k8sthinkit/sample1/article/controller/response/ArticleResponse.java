package com.creationline.k8sthinkit.sample1.article.controller.response;

import lombok.NonNull;
import lombok.Value;

/**
 * 記事内容
 */
@Value
@NonNull
public class ArticleResponse {

    /** 記事タイトル */
    private final String title;

    /** 著者 */
    private final String author;

    /** 記事本文 */
    private final String body;

}
