package com.creationline.k8sthinkit.sample1.article.controller.response;

import org.springframework.http.HttpStatus;

import lombok.NonNull;
import lombok.Value;

@Value
@NonNull
public class ClientErrorResponse {

    /** ステータスコード */
    private HttpStatus statusCode;

    /** エラーメッセージ */
    private String message;

}
