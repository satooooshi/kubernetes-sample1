package com.creationline.k8sthinkit.sample1.rank.controller.response;

import lombok.Value;

/**
 * 順位表の項目
 */
@Value
public class RankEntry {

    /** 記事ID */
    private Long articleId;

    /** アクセス数 */
    private Long access;

    /** タイトル */
    private String title;

    /** 著者 */
    private String author;

}
