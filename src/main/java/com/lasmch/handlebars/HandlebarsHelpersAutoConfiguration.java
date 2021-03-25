//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lasmch.handlebars;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

@Configuration
@ConditionalOnBean({HandlebarsViewResolver.class})
public class HandlebarsHelpersAutoConfiguration {
    public HandlebarsHelpersAutoConfiguration() {
    }

    @Bean
    public BeanPostProcessor handlebarBeanPostProcessor(final HandlebarsViewResolver viewResolver) {
        return new BeanPostProcessor() {
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                HandlebarsHelper annotation = (HandlebarsHelper)AnnotationUtils.findAnnotation(bean.getClass(), HandlebarsHelper.class);
                if (annotation != null) {
                    viewResolver.registerHelpers(bean);
                }

                return bean;
            }
        };
    }
}
