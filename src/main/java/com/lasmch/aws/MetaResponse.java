//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lasmch.aws;

public class MetaResponse {
    String md5;
    String version;
    String etag;
    String sseAlgorithm;

    public String getMd5() {
        return this.md5;
    }

    public String getVersion() {
        return this.version;
    }

    public String getEtag() {
        return this.etag;
    }

    public String getSseAlgorithm() {
        return this.sseAlgorithm;
    }

    public MetaResponse(final String md5, final String version, final String etag, final String sseAlgorithm) {
        this.md5 = md5;
        this.version = version;
        this.etag = etag;
        this.sseAlgorithm = sseAlgorithm;
    }
}
