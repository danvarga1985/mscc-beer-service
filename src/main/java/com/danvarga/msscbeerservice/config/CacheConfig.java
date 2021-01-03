package com.danvarga.msscbeerservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

// Can also be set on Application level.
@EnableCaching
@Configuration
public class CacheConfig {

}
