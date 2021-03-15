package com.creationline.k8sthinkit.sample1.accesscount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring applicationを起動するエントリーポイント.
 */
@SpringBootApplication
public class AccesscountApplication {

	/** ログ出力 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AccesscountApplication.class);

	/**
	 * エントリーポイント.
	 * 
	 * @param args コマンド引数
	 */
	public static void main(String[] args) {
		SpringApplication.run(AccesscountApplication.class, args);
		LOGGER.debug("Accesscount application started!");
	}

}
