package com.creationline.k8sthinkit.sample1.rank.service.accesscount;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Accesscountサービスの設定値を読み込むクラス
 */
@Component
@ConfigurationProperties("accesscount")
@Data
public class AccesscountConfiguration {

    /** 接続先URL */
    private String url;

}
