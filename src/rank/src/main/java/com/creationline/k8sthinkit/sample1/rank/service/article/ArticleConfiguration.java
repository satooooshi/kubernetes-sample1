package com.creationline.k8sthinkit.sample1.rank.service.article;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Articleサービスへの接続設定を読み込むクラス
 */
@Component
@ConfigurationProperties("article")
@Data
public class ArticleConfiguration {

    /** 接続先URL */
    private String url;

}
