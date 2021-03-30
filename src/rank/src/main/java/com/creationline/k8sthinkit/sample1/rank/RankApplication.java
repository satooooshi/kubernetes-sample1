package com.creationline.k8sthinkit.sample1.rank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring applicationを起動するエントリーポイント
 */
@SpringBootApplication
@EnableScheduling
public class RankApplication {

	/** ログ出力 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RankApplication.class);

	/**
	 * エントリーポイント
	 * 
	 * @param args コマンド引数
	 */
	public static void main(String[] args) {

		SpringApplication.run(RankApplication.class, args);

		LOGGER.debug("Rank application started!");

	}

}
