package com.cnc.qr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Qr Server.
 *
 * <p>
 * Properties are configured in the {@code application.yml} file. See {@link
 * io.github.jhipster.config.JHipsterProperties} for a good example.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
