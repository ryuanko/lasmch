package com.lasmch.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "aws",
        value = {"enabled"}
)
@EnableConfigurationProperties({ClientConfig.class})
public class AwsS3ClientAutoConfiguration {
    final ClientConfig clientConfig;
    final ClientConfiguration clientConfiguration;

    @Bean
    @ConditionalOnMissingBean
    public AwsClient awsClient() {
        return new AwsClient(this.clientConfig, this.clientConfiguration);
    }

    public AwsS3ClientAutoConfiguration(final ClientConfig clientConfig, final ClientConfiguration clientConfiguration) {
        this.clientConfig = clientConfig;
        this.clientConfiguration = clientConfiguration;
    }

    static class AwsS3ClientConfiguration {
        AwsS3ClientConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        public ClientConfiguration clientConfiguration() {
            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setMaxConnections(100);
            configuration.setConnectionTimeout(2000);
            configuration.setConnectionMaxIdleMillis(3000L);
            configuration.setProtocol(Protocol.HTTPS);
            return configuration;
        }
    }
}
