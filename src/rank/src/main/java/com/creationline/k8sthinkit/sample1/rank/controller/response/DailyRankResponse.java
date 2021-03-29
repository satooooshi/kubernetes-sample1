package com.creationline.k8sthinkit.sample1.rank.controller.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Value;

@Value
public class DailyRankResponse {
    private LocalDate date;
    private List<RankEntry> total;
    private List<RankEntry> unique;
}
