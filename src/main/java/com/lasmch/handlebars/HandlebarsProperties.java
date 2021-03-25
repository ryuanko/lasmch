//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lasmch.handlebars;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "spring.handlebars"
)
public class HandlebarsProperties {
    String prefix;
    String suffix;
    boolean cache;
    String delimiter = "{{,}}";

    public HandlebarsProperties() {
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public boolean isCache() {
        return this.cache;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public void setCache(final boolean cache) {
        this.cache = cache;
    }

    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }
}
