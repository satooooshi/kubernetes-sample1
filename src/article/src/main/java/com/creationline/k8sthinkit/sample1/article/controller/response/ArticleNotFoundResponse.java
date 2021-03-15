package com.creationline.k8sthinkit.sample1.article.controller.response;

import lombok.NonNull;
import lombok.Value;

/**
 * 目的の記事が存在しない場合の例外
 * 
 * ID等で特定の記事1件を取得するAPIにおいて該当する記事がなかった場合に用いる。
 * 検索や一覧など「結果が0件であることも想定されるAPI」において該当する記事がなかった場合には用いない。
 */
@Value
@NonNull
public class ArticleNotFoundResponse {

    /** エラーメッセージ */
    private String message;

}
