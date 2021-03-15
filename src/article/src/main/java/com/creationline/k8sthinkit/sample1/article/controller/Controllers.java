package com.creationline.k8sthinkit.sample1.article.controller;

/**
 * Controllerで用いる雑多なユーティリティと定数
 */
public class Controllers {

    /**
     * このアプリケーションが入出力するデータのMIME Type
     */
    private static final String HANDLING_MIMETYPE = "application/json";

    /**
     * このAPIがリクエストで受け付けるMIME Type
     */
    public static final String MIMETYPE_CONSUMING = HANDLING_MIMETYPE;

    /**
     * このAPIがレスポンスで生成するMIME Type
     */
    public static final String MIMETYPE_PRODUCING = HANDLING_MIMETYPE;

}
