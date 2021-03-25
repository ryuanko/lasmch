package com.lasmch.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
@ConfigurationProperties(
        prefix = "aws"
)
public class ClientConfig {
    private String accessKey;
    private String secretAccessKey;
    private String bucket;
    private String role;
    private String profile;
    private Regions region;
    private boolean useKms;
    private boolean instance;
    private int daysAfterInitiation;
    private String sseAlgorithm;
    private Duration expiration;

    private static String $default$sseAlgorithm() {
        return ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION;
    }

    private static Duration $default$expiration() {
        return Duration.ofHours(1L);
    }

    public static ClientConfig.ClientConfigBuilder builder() {
        return new ClientConfig.ClientConfigBuilder();
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public String getSecretAccessKey() {
        return this.secretAccessKey;
    }

    public String getBucket() {
        return this.bucket;
    }

    public String getRole() {
        return this.role;
    }

    public String getProfile() {
        return this.profile;
    }

    public Regions getRegion() {
        return this.region;
    }

    public boolean isUseKms() {
        return this.useKms;
    }

    public boolean isInstance() {
        return this.instance;
    }

    public int getDaysAfterInitiation() {
        return this.daysAfterInitiation;
    }

    public String getSseAlgorithm() {
        return this.sseAlgorithm;
    }

    public Duration getExpiration() {
        return this.expiration;
    }

    public void setAccessKey(final String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretAccessKey(final String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public void setBucket(final String bucket) {
        this.bucket = bucket;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public void setProfile(final String profile) {
        this.profile = profile;
    }

    public void setRegion(final Regions region) {
        this.region = region;
    }

    public void setUseKms(final boolean useKms) {
        this.useKms = useKms;
    }

    public void setInstance(final boolean instance) {
        this.instance = instance;
    }

    public void setDaysAfterInitiation(final int daysAfterInitiation) {
        this.daysAfterInitiation = daysAfterInitiation;
    }

    public void setSseAlgorithm(final String sseAlgorithm) {
        this.sseAlgorithm = sseAlgorithm;
    }

    public void setExpiration(final Duration expiration) {
        this.expiration = expiration;
    }

    public ClientConfig(final String accessKey, final String secretAccessKey, final String bucket, final String role, final String profile, final Regions region, final boolean useKms, final boolean instance, final int daysAfterInitiation, final String sseAlgorithm, final Duration expiration) {
        this.accessKey = accessKey;
        this.secretAccessKey = secretAccessKey;
        this.bucket = bucket;
        this.role = role;
        this.profile = profile;
        this.region = region;
        this.useKms = useKms;
        this.instance = instance;
        this.daysAfterInitiation = daysAfterInitiation;
        this.sseAlgorithm = sseAlgorithm;
        this.expiration = expiration;
    }

    public ClientConfig() {
        this.sseAlgorithm = $default$sseAlgorithm();
        this.expiration = $default$expiration();
    }

    public static class ClientConfigBuilder {
        private String accessKey;
        private String secretAccessKey;
        private String bucket;
        private String role;
        private String profile;
        private Regions region;
        private boolean useKms;
        private boolean instance;
        private int daysAfterInitiation;
        private boolean sseAlgorithm$set;
        private String sseAlgorithm;
        private boolean expiration$set;
        private Duration expiration;

        ClientConfigBuilder() {
        }

        public ClientConfig.ClientConfigBuilder accessKey(final String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        public ClientConfig.ClientConfigBuilder secretAccessKey(final String secretAccessKey) {
            this.secretAccessKey = secretAccessKey;
            return this;
        }

        public ClientConfig.ClientConfigBuilder bucket(final String bucket) {
            this.bucket = bucket;
            return this;
        }

        public ClientConfig.ClientConfigBuilder role(final String role) {
            this.role = role;
            return this;
        }

        public ClientConfig.ClientConfigBuilder profile(final String profile) {
            this.profile = profile;
            return this;
        }

        public ClientConfig.ClientConfigBuilder region(final Regions region) {
            this.region = region;
            return this;
        }

        public ClientConfig.ClientConfigBuilder useKms(final boolean useKms) {
            this.useKms = useKms;
            return this;
        }

        public ClientConfig.ClientConfigBuilder instance(final boolean instance) {
            this.instance = instance;
            return this;
        }

        public ClientConfig.ClientConfigBuilder daysAfterInitiation(final int daysAfterInitiation) {
            this.daysAfterInitiation = daysAfterInitiation;
            return this;
        }

        public ClientConfig.ClientConfigBuilder sseAlgorithm(final String sseAlgorithm) {
            this.sseAlgorithm = sseAlgorithm;
            this.sseAlgorithm$set = true;
            return this;
        }

        public ClientConfig.ClientConfigBuilder expiration(final Duration expiration) {
            this.expiration = expiration;
            this.expiration$set = true;
            return this;
        }

        public ClientConfig build() {
            String sseAlgorithm = this.sseAlgorithm;
            if (!this.sseAlgorithm$set) {
                sseAlgorithm = ClientConfig.$default$sseAlgorithm();
            }

            Duration expiration = this.expiration;
            if (!this.expiration$set) {
                expiration = ClientConfig.$default$expiration();
            }

            return new ClientConfig(this.accessKey, this.secretAccessKey, this.bucket, this.role, this.profile, this.region, this.useKms, this.instance, this.daysAfterInitiation, sseAlgorithm, expiration);
        }

        public String toString() {
            return "ClientConfig.ClientConfigBuilder(accessKey=" + this.accessKey + ", secretAccessKey=" + this.secretAccessKey + ", bucket=" + this.bucket + ", role=" + this.role + ", profile=" + this.profile + ", region=" + this.region + ", useKms=" + this.useKms + ", instance=" + this.instance + ", daysAfterInitiation=" + this.daysAfterInitiation + ", sseAlgorithm=" + this.sseAlgorithm + ", expiration=" + this.expiration + ")";
        }
    }
}
