
package com.se.besearchapp.config;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = { "com.se.besearchapp.repository" })
@ComponentScan(basePackages = { "com.se.besearchapp" })
public class ElasticSerachConfig {

	@Value("${elasticsearch.domain}")
	private String elasticSearchDomain;

	@Value("${elasticsearch.port}")
	private Integer elasticSearchPort;

	@Value("${elasticsearch.protocol}")
	private String elasticSearchProtocol;

	@Value("${elasticsearch.username}")
	private String elasticSearchUsername;

	@Value("${elasticsearch.password}")
	private String elasticSearchPassword;


	@Bean
	public RestHighLevelClient createSimpleElasticClient() throws Exception {

		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials("phunghx", "hoangtn@01"));

		SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true);
		final SSLContext sslContext = sslBuilder.build();
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(elasticSearchDomain, 9200, "https"))
						.setHttpClientConfigCallback(new HttpClientConfigCallback() {
							@Override
							public HttpAsyncClientBuilder customizeHttpClient(
									HttpAsyncClientBuilder httpClientBuilder) {
								return httpClientBuilder.setSSLContext(sslContext)
										.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
										.setDefaultCredentialsProvider(credentialsProvider);
							}
						}).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
							@Override
							public RequestConfig.Builder customizeRequestConfig(
									RequestConfig.Builder requestConfigBuilder) {
								return requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(120000);
							}
						}));
		System.out.println("elasticsearch client created");
		return client;
	}

}
