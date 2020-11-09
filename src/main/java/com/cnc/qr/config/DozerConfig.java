package com.cnc.qr.config;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean定義用クラス.
 */
@Configuration
public class DozerConfig {

    /**
     * BozerのBean定義.
     */
    @Bean("DozerBeanMapper")
    public Mapper mapper() throws Exception {
        Mapper mapper = DozerBeanMapperBuilder
            .create()
            .withMappingBuilder(mappingBuilder)
            .build();
        return mapper;
    }

    /**
     * Bozerのマッピング定義が必要な場合、configureメソッドに下記のように書く.
     * <pre>
     * {@code
     * protected void configure() {
     * mapping(type(Source.class),
     * type(Dest.class),
     * TypeMappingOptions.oneWay())
     * .fields(field("id"), field("destinationId"))
     * .fields(field("name"), field("destinationName"));
     * }
     * }
     * </pre>
     */
    BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
        @Override
        protected void configure() {
        }
    };
}
