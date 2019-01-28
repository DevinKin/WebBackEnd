package com.devinkin.springboot06datamybatis.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

@MapperScan(value = "com.devinkin.springboot06datamybatis.mapper")
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                // 启动驼峰命名法规则
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
