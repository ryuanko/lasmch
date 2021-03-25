//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lasmch.handlebars;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.URLTemplateLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnProperty(
        name = {"spring.handlebars.enabled"},
        matchIfMissing = true
)
@EnableConfigurationProperties({HandlebarsProperties.class})
public class HandlebarsAutoConfiguration {
    private final HandlebarsProperties properties;

    public HandlebarsAutoConfiguration(HandlebarsProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    Handlebars handlebars() {
        Handlebars handlebars = (new Handlebars(this.findLoder(this.properties.prefix, this.properties.suffix))).with(EscapingStrategy.NOOP).with(EscapingStrategy.XML);
        if (this.properties.delimiter != null) {
            String[] delimiters = this.properties.delimiter.split(",");
            handlebars.setStartDelimiter(delimiters[0]);
            handlebars.setEndDelimiter(delimiters[1]);
        }

        StringHelpers.register(handlebars);
        ConditionalHelpers[] var6 = ConditionalHelpers.values();
        int var3 = var6.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            ConditionalHelpers h = var6[var4];
            handlebars.registerHelper(h.name(), ConditionalHelpers.valueOf(h.name()));
        }

        return handlebars;
    }

    @Bean
    @ConditionalOnMissingBean
    HandlebarsViewResolver handlebarsViewResolver() {
        HandlebarsViewResolver resolver = new HandlebarsViewResolver(this.handlebars());
        resolver.setCache(this.properties.cache);
        resolver.setOrder(-2147483648);
        return resolver;
    }

    URLTemplateLoader findLoder(String prefix, String suffix) {
        if (prefix.startsWith("classpath:")) {
            return new ClassPathTemplateLoader(prefix.substring("classpath:".length()), suffix);
        } else if (prefix.startsWith("file:")) {
            return new FileTemplateLoader(prefix.substring("file:".length()), suffix);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
