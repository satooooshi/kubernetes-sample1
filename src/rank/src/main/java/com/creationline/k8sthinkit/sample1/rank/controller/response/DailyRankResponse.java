package com.creationline.k8sthinkit.sample1.rank.controller.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Value;

/**
 * 
 */
@Value
public class DailyRankResponse {

    /** 日付 */
    private LocalDate date;

    /** 延べアクセス順位表 */
    private List<RankEntry> total;

    /** アクセスユーザ順位表 */
    private List<RankEntry> unique;
}
