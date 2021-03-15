package com.creationline.k8sthinkit.sample1.article.controller;

/**
 * 指定された記事が存在しない場合に使用する例外
 */
public class ArticleNotFoundException extends RuntimeException {


    /** 自動生成serialVersionUID */
    private static final long serialVersionUID = -8706883156190104574L;

    /** 標準的な例外クラスのコンストラクタ */
    public ArticleNotFoundException() {
        super();
    }

    /** 標準的な例外クラスのコンストラクタ */
    public ArticleNotFoundException(final String message) {
        super(message);
    }

    /** 標準的な例外クラスのコンストラクタ */
    public ArticleNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /** 標準的な例外クラスのコンストラクタ */
    protected ArticleNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /** 標準的な例外クラスのコンストラクタ */
    public ArticleNotFoundException(final Throwable cause) {
        super(cause);
    }

}
