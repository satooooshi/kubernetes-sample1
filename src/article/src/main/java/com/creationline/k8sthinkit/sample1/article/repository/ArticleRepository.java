package com.creationline.k8sthinkit.sample1.article.repository;

import com.creationline.k8sthinkit.sample1.article.repository.entity.Article;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * 記事を永続化する
 * 
 * Spring Data R2DBCの機能により基本的なメソッドは自動作成される
 */
@Repository
public interface ArticleRepository extends ReactiveSortingRepository<Article, Long> {

}
