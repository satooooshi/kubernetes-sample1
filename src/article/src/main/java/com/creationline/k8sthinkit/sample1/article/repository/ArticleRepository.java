package com.creationline.k8sthinkit.sample1.article.repository;

import com.creationline.k8sthinkit.sample1.article.repository.entity.ArticleEntity;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ReactiveSortingRepository<ArticleEntity, Long> {

}
