package com.creationline.k8sthinkit.sample1.rank.service.article;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("article")
@Data
public class ArticleConfiguration {

    private String url;

}
