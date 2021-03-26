package com.creationline.k8sthinkit.sample1.rank;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

@SpringBootTest(properties="spring.profiles.active=test")
class RankApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(RankApplicationTests.class);

	private final ApplicationContext context;

	public RankApplicationTests(
		@NonNull //
		final ApplicationContext context //
	) {

		this.context = context;

	}

	@Test
	void contextLoads() {
		final String message = this.context.getEnvironment().getProperty("profiletest.message", "failed to load message from application.yaml");
		LOGGER.info("profile test message: {}", message);
	}

}
