package com.creationline.k8sthinkit.sample1.rank.repository.entity;

import java.time.LocalDate;

import lombok.NonNull;
import lombok.Value;

@Value
public class DailyEntryId {
    @NonNull
    private LocalDate date;
    @NonNull
    private Integer rank;
}
