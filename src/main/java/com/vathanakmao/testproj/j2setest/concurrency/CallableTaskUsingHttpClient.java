package com.vathanakmao.testproj.j2setest.concurrency;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.GetMethod;

public class CallableTaskUsingHttpClient implements Callable<Boolean> {
    private final int timeout;

    public CallableTaskUsingHttpClient() {
        timeout = 10000;
    }

    public CallableTaskUsingHttpClient(int timeout) {
        this.timeout = timeout;
    }

    public Boolean call() throws IOException, HttpException {
        HttpClient client = new HttpClient();

        HostConfiguration hostconfig = new HostConfiguration();
        hostconfig.setHost("www.yahoo.com");
        hostconfig.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_0);

        HttpMethod method = new GetMethod("/");
        method.getParams().setParameter("http.socket.timeout", timeout);

        try {
            client.executeMethod(hostconfig, method);
        }
        finally {
            method.releaseConnection();
        }

        return true;
    }
}
