package com.andver.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class Config
{
    @Bean
    public RestTemplate appRestTemplate(CloseableHttpClient httpClient)
    {
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }

    @Bean
    public CloseableHttpClient httpClient(HttpClientConnectionManager connectionManager)
    {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(30000).build();

        HttpHost proxy = new HttpHost("localhost", 8081);

        return HttpClients.custom()
                .useSystemProperties()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setProxy(proxy)
                .evictExpiredConnections()
                .evictIdleConnections(30000, TimeUnit.MILLISECONDS)
                .build();
    }

    @Bean
    public HttpClientConnectionManager connectionPool()
    {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(50);
        connectionManager.setMaxTotal(50);
        return connectionManager;
    }
}