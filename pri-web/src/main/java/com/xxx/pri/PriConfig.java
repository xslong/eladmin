package com.xxx.pri;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.xxx.pri"})
@EntityScan({"com.xxx.pri", "me.zhengjie"})
@EnableJpaRepositories({"com.xxx.pri.**.repository", "me.zhengjie"})
public class PriConfig implements WebMvcConfigurer {
}
