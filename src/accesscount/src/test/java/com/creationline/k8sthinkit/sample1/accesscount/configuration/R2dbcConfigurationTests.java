package com.creationline.k8sthinkit.sample1.accesscount.configuration;

import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.converter.AccesscountReadConverter;
import com.creationline.k8sthinkit.sample1.accesscount.repository.entity.converter.AccesscountWriteConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties="spring.profiles.active=test")
public class R2dbcConfigurationTests {

    private final R2dbcConfiguration r2dbcConfiguration;

    public R2dbcConfigurationTests(

        @Autowired
        final R2dbcConfiguration r2dbcConfiguration

    ) {

        this.r2dbcConfiguration = r2dbcConfiguration;

    }

    @Test
    public void testGetCustomConvertersReturnsExpectedConverters() {

        final List<Class<?>> expectedConverterClasses = List.of(
                AccesscountReadConverter.class,
                AccesscountWriteConverter.class
        );

        final List<Object> actual = this.r2dbcConfiguration.getCustomConverters();

        for (final Class<?> expectedConverterClass : expectedConverterClasses) {

            final var existExpectedConverter = actual.stream() //
                    .anyMatch(expectedConverterClass::isInstance);
            assertTrue(existExpectedConverter, "expected converter instance (class: " + expectedConverterClass + ") is not found");

        }

    }

}
