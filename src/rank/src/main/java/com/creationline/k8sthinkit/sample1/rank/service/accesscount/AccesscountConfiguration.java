package com.creationline.k8sthinkit.sample1.rank.service.accesscount;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("accesscount")
@Data
public class AccesscountConfiguration {

    private String url;

}
