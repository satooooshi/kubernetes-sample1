package com.creationline.k8sthinkit.sample1.article.controller;

public class ArticleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8706883156190104574L;
    public ArticleNotFoundException() {}
    public ArticleNotFoundException(final String message) {
        super(message);
    }
    public ArticleNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
    protected ArticleNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public ArticleNotFoundException(final Throwable cause) {
        super(cause);
    }
}
