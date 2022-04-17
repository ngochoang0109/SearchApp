package com.se.besearchapp.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.se.besearchapp.helpers.ApiRes;
import com.se.besearchapp.request.ElasticReq;

@Service
public class ElasticSearchServiceImpl {

	/*
	 * @Autowired private RestHighLevelClient client;
	 */

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

	public ApiRes<Object> initElasticSearch(ElasticReq req) throws Exception {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(elasticSearchDomain, elasticSearchPort, elasticSearchProtocol)));
		BulkRequest bulkReq = new BulkRequest();
		String index = req.getIndex();
		bulkReq.add(new IndexRequest("posts").id("1").source(XContentType.JSON, "field", "foo"));
		bulkReq.add(new IndexRequest("posts").id("2").source(XContentType.JSON, "field", "bar"));
		bulkReq.add(new IndexRequest("posts").id("3").source(XContentType.JSON, "field", "baz"));
		client.bulk(bulkReq, RequestOptions.DEFAULT);
		client.close();
		return null;

	}

	/*
	 * public ApiRes<Object> addIndex(ElasticReq req) throws IOException {
	 * 
	 * ApiRes<Object> apiRes = new ApiRes<Object>(); try {
	 * 
	 * BulkRequest bulkReq = new BulkRequest(); String index = req.getIndex(); Gson
	 * g = new Gson(); for (Object x : req.getDataSource()) { java.util.Map<String,
	 * Object> map = (java.util.Map<String, Object>) g.fromJson(g.toJson(x),
	 * Object.class); bulkReq.add( new
	 * IndexRequest(index).id(map.get("id").toString()).source(g.toJson(x),
	 * XContentType.JSON)); }
	 * 
	 * BulkRequest bulkReq = new BulkRequest(); bulkReq.add(new
	 * IndexRequest("posts").id("1").source(XContentType.JSON, "field", "foo"));
	 * bulkReq.add(new IndexRequest("posts").id("2").source(XContentType.JSON,
	 * "field", "bar")); bulkReq.add(new
	 * IndexRequest("posts").id("3").source(XContentType.JSON, "field", "baz"));
	 * client.bulk(bulkReq, RequestOptions.DEFAULT); client.close();
	 * 
	 * } catch (Exception e) { apiRes.setError(true);
	 * apiRes.setErrorReason(e.getMessage()); }
	 * 
	 * return apiRes; }
	 */

}
