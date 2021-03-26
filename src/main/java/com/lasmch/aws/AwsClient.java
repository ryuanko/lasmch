package com.lasmch.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider.Builder;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AbortIncompleteMultipartUpload;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration.Rule;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Slf4j
public class AwsClient {
    private ClientConfig config;
    private AmazonS3 s3client;

    public AwsClient(ClientConfig config) {
        this(config, (ClientConfiguration)null);
    }

    public AwsClient(ClientConfig config, ClientConfiguration configuration) {

        if (config.getBucket().equals("lasmch-la")) {
            config.setRegion(Regions.US_EAST_2);
        }

        System.out.println("getBucket:" + config.getBucket());
        System.out.println("getAccessKey:" + config.getAccessKey());
        System.out.println("getSecretAccessKey:" + config.getSecretAccessKey());
        System.out.println("getRegion:" + config.getRegion());

        this.config = config;
        this.s3client = this.createClient(configuration);
        if (config.getDaysAfterInitiation() > 0) {
            this.s3client.setBucketLifecycleConfiguration(config.getBucket(), this.createLifeCycle());
        }

    }

    private BucketLifecycleConfiguration createLifeCycle() {
        Rule rule = new Rule();
        rule.withStatus("Enabled").withAbortIncompleteMultipartUpload((new AbortIncompleteMultipartUpload()).withDaysAfterInitiation(this.config.getDaysAfterInitiation()));
        return new BucketLifecycleConfiguration(Arrays.asList(rule));
    }

    private AWSCredentialsProvider getProvider() {
        if (this.config.getAccessKey() != null && this.config.getSecretAccessKey() != null) {
            AWSCredentials credentials = new BasicAWSCredentials(this.config.getAccessKey(), this.config.getSecretAccessKey());
            return new AWSStaticCredentialsProvider(credentials);
        } else if (this.config.getProfile() != null) {
            return new ProfileCredentialsProvider(this.config.getProfile());
        } else {
            return this.config.isInstance() ? new InstanceProfileCredentialsProvider(false) : null;
        }
    }

    private AWSCredentialsProvider getAssumeRole(AWSCredentialsProvider provider) {
        if (this.config.getRole() != null) {
            AWSSecurityTokenServiceClientBuilder tokenServiceClientBuilder = AWSSecurityTokenServiceClientBuilder.standard();
            tokenServiceClientBuilder.setRegion(this.config.getRegion().toString());
            AWSSecurityTokenService stsClient = (AWSSecurityTokenService)((AWSSecurityTokenServiceClientBuilder)tokenServiceClientBuilder.withCredentials(provider)).build();
            STSAssumeRoleSessionCredentialsProvider roleSessionCredentialsProvider = (new Builder(this.config.getRole(), UUID.randomUUID().toString())).withStsClient(stsClient).build();
            return roleSessionCredentialsProvider;
        } else {
            return provider;
        }
    }

    public AmazonS3 createClient(ClientConfiguration configuration) {
        if (this.config.getRegion() == null) {
            this.config.setRegion(Regions.AP_NORTHEAST_2);
        }

        AWSCredentialsProvider provider = this.getAssumeRole(this.getProvider());
        return provider == null ? (AmazonS3)((AmazonS3ClientBuilder)((AmazonS3ClientBuilder)AmazonS3ClientBuilder.standard().withRegion(this.config.getRegion())).withClientConfiguration(configuration)).build() : (AmazonS3)((AmazonS3ClientBuilder)((AmazonS3ClientBuilder)((AmazonS3ClientBuilder)AmazonS3ClientBuilder.standard().withRegion(this.config.getRegion())).withClientConfiguration(configuration)).withCredentials(provider)).build();
    }

    public MetaResponse upStream(String keyName, File file) {
        PutObjectResult result = this.s3client.putObject(this.config.getBucket(), keyName, file);
        return new MetaResponse(result.getContentMd5(), result.getVersionId(), result.getETag(), result.getSSEAlgorithm());
    }

    public MetaResponse upStream(String keyName, InputStream in, String contentType) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength((long)in.available());
        meta.setContentType(contentType);
        if (this.config.isUseKms()) {
            meta.setSSEAlgorithm(this.config.getSseAlgorithm());
        }

        PutObjectRequest request = new PutObjectRequest(this.config.getBucket(), keyName, in, meta);
        PutObjectResult result = this.s3client.putObject(request);
        return new MetaResponse(result.getContentMd5(), result.getVersionId(), result.getETag(), result.getSSEAlgorithm());
    }

    public Response downStream(String keyName) {
        S3Object s3Object = this.s3client.getObject(this.config.getBucket(), keyName);
        ObjectMetadata meta = s3Object.getObjectMetadata();
        return new Response(meta.getContentLength(), meta.getContentType(), s3Object.getObjectContent());
    }

    private Stream<S3ObjectSummary> toStream(String path, int limit) {
        ListObjectsV2Request request = (new ListObjectsV2Request()).withBucketName(this.config.getBucket()).withFetchOwner(true).withPrefix(path.equals(this.config.getBucket()) ? "" : path).withMaxKeys(limit);
        return this.s3client.listObjectsV2(request).getObjectSummaries().stream();
    }

    public List<String> list(int limit) {
        return this.list(this.config.getBucket(), limit);
    }

    public List<String> list(String path, int limit) {
        return (List)this.toStream(path, limit).map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    public <T> List<T> list(int limit, Function<S3ObjectSummary, T> function, Comparator<S3ObjectSummary> comparator) {
        return this.list(this.config.getBucket(), limit, function, comparator);
    }

    public <T> List<T> list(String path, int limit, Function<S3ObjectSummary, T> function, Comparator<S3ObjectSummary> comparator) {
        return (List)this.toStream(path, limit).sorted(comparator).map(function).collect(Collectors.toList());
    }

    public <T> List<T> list(int limit, Function<S3ObjectSummary, T> function) {
        return this.list(this.config.getBucket(), limit, function);
    }

    public <T> List<T> list(String path, int limit, Function<S3ObjectSummary, T> function) {
        return (List)this.toStream(path, limit).map(function).collect(Collectors.toList());
    }

    public void delete(String... keyName) {
        DeleteObjectsRequest request = (new DeleteObjectsRequest(this.config.getBucket())).withKeys(keyName);
        this.s3client.deleteObjects(request);
    }

    public URL getSignedUrl(String key) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += this.config.getExpiration().toMillis();
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = (new GeneratePresignedUrlRequest(this.config.getBucket(), key)).withMethod(HttpMethod.GET).withExpiration(expiration);
        return this.s3client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public ClientConfig getConfig() {
        return this.config;
    }

    public AmazonS3 getS3client() {
        return this.s3client;
    }
}
