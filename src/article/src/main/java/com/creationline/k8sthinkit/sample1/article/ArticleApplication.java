package com.creationline.k8sthinkit.sample1.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring applicationを起動するエントリーポイント
 */
@SpringBootApplication
public class ArticleApplication {

	/** ログ出力 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleApplication.class);

	/**
	 * エントリーポイント
	 * 
	 * @param args コマンド引数
	 */
	public static void main(@NonNull final String[] args) {
		SpringApplication.run(ArticleApplication.class, args);
		LOGGER.info("Article application started!");
	}

}
