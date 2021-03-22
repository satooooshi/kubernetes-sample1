package com.creationline.k8sthinkit.sample1.accesscount.repository.entity;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.NonNull;
import lombok.Value;

@Value
@Table("accesscount")
public class Accesscount {

    @Id
    private Long id;

    @NonNull
    @Column("article_id")
    private Long articleId;

    @NonNull
    private String uid;

    @NonNull
    @Column("access_at")
    private OffsetDateTime accessAt;

}
