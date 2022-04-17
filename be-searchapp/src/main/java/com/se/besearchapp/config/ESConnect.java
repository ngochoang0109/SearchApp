/*
 * package com.se.besearchapp.config;
 * 
 * import org.elasticsearch.client.RestHighLevelClient; import
 * org.springframework.data.elasticsearch.client.ClientConfiguration; import
 * org.springframework.data.elasticsearch.client.RestClients; import
 * org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
 * 
 * public class ESConnect {
 * 
 * private RestHighLevelClient client; private ElasticsearchRestTemplate
 * elasticsearchRestTemplate;
 * 
 * private ESConnect() { ClientConfiguration clientConfiguration =
 * ClientConfiguration.builder().connectedTo("10.1.4.41:9200").build(); client =
 * RestClients.create(clientConfiguration).rest(); elasticsearchRestTemplate =
 * new ElasticsearchRestTemplate(client); // client = new
 * RestHighLevelClient(RestClient.builder(new HttpHost("10.1.4.41", 9200))); }
 * 
 * private static class ESConnectHolder { private static final ESConnect
 * instance = new ESConnect(); }
 * 
 * public static ESConnect getInstance() { return ESConnectHolder.instance; }
 * 
 * public RestHighLevelClient getClient() { return this.client; }
 * 
 * public ElasticsearchRestTemplate getRestTemplate() { return
 * this.elasticsearchRestTemplate; } }
 */