package com.axonkafka.config;

import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(value = { AxonAutoConfiguration.class })
public class AxonConfig {

}