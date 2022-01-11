package twilio.config;

import com.twilio.http.NetworkHttpClient;
import com.twilio.http.TwilioRestClient;
import org.apache.catalina.connector.Connector;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.concurrent.TimeUnit;

import static twilio.config.TwilioProperties.ACCOUNT_SID;
import static twilio.config.TwilioProperties.AUTH_TOKEN;

@Configuration
public class Config
{
    @Value("${server.http.port}")
    private int httpPort;

    @Bean
    public HttpClientConnectionManager connectionManager()
    {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(50);
        connectionManager.setMaxTotal(50);
        return connectionManager;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(HttpClientConnectionManager connectionManager)
    {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(30000).build();
        return HttpClientBuilder.create()
                .useSystemProperties()
                .setProxy(new HttpHost("localhost", 8081))
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .evictExpiredConnections()
                .evictIdleConnections(30000, TimeUnit.MILLISECONDS);
    }

    @Bean
    public TwilioRestClient twilioRestClient(HttpClientBuilder httpClientBuilder)
    {
        return new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_TOKEN)
                .httpClient(new NetworkHttpClient(httpClientBuilder))
                .build();
    }

    @Bean
    public Filter twilioRequestFilter()
    {
        return new TwilioRequestFilter();
    }

    /**
     * Creates ServletWebServerFactory with new connector in addition
     * to already configured via standard way
     */
    @Bean
    public ServletWebServerFactory servletContainer()
    {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    /**
     * Creates Tomcat connector for http protocol
     */
    private Connector createStandardConnector()
    {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setPort(httpPort);
        return connector;
    }

}
