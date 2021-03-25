//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.lasmch.aws;

import java.io.InputStream;

public class Response {
    long size;
    String contentType;
    InputStream inputStream;

    public long getSize() {
        return this.size;
    }

    public String getContentType() {
        return this.contentType;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public Response(final long size, final String contentType, final InputStream inputStream) {
        this.size = size;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }
}
