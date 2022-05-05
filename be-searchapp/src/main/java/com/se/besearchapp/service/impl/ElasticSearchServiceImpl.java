package com.se.besearchapp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.se.besearchapp.helpers.ApiRes;
import com.se.besearchapp.helpers.ImportJsonReq;
import com.se.besearchapp.pojo.DataSource;
import com.se.besearchapp.request.ElasticDatasourceReq;
import com.se.besearchapp.request.ElasticReq;
import com.se.besearchapp.request.FilterParam;
import com.se.besearchapp.request.FilterReq;
import com.se.besearchapp.service.ElasticSearchService;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

	@Autowired
	private RestHighLevelClient client;

	public ApiRes<Object> saveMulDatasource(ElasticReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			BulkRequest bulkReq = new BulkRequest();
			String index = req.getIndex();
			Gson g = new Gson();
			for (DataSource dataSource : req.getDataSource()) {
				bulkReq.add(new IndexRequest(index).id(String.valueOf(dataSource.getId())).source(g.toJson(dataSource),
						XContentType.JSON));
			}
			client.bulk(bulkReq, RequestOptions.DEFAULT);

		} catch (Exception e) {

		}
		apiRes.setObject(true);
		return apiRes;
	}

	@Override
	public ApiRes<Object> saveSingleDatasource(ElasticDatasourceReq req) {

		ApiRes<Object> apiRes = new ApiRes<Object>();

		try {
			BulkRequest bulkReq = new BulkRequest();
			String index = req.getIndex();
			Gson g = new Gson();
			bulkReq.add(new IndexRequest(index).id(String.valueOf(req.getDataSource().getId()))
					.source(g.toJson(req.getDataSource()), XContentType.JSON));
			client.bulk(bulkReq, RequestOptions.DEFAULT);

		} catch (Exception e) {

		}

		return apiRes;
	}

	@Override
	public ApiRes<Object> deleteSingleDatasource(String index, long id) {
		ApiRes<Object> apiRes = new ApiRes<Object>();

		try {
			client.delete(new DeleteRequest(index, String.valueOf(id)), RequestOptions.DEFAULT);

		} catch (Exception e) {

		}

		return apiRes;
	}

	@Override
	public ApiRes<Object> deleteMulDatasourceFromIndex(String index, List<Integer> ids) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			if (ids != null && !ids.isEmpty()) {
				BulkRequest bulkReq = new BulkRequest();
				for (Integer id : ids) {
					bulkReq.add(new DeleteRequest(index).id(String.valueOf(id)));
					client.bulk(bulkReq, RequestOptions.DEFAULT);
				}
			} else {
				apiRes.setErrorReason("List id not empty");
			}
		} catch (IOException e) {

		}
		return apiRes;
	}

	@Override
	public ApiRes<Object> deleteAllDatasourceFromIndex(String index) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
//		try {
//			
//			client.delete(new DeleteRequest(index), RequestOptions.DEFAULT);
//
//		} catch (IOException e) {
//			apiRes.setError(true);
//			apiRes.setErrorReason(e.getMessage());
//		}
		return apiRes;
	}

	@Override
	public ApiRes<Object> search(FilterReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			List<Object> result = new ArrayList<Object>();

			SearchRequest searchRequest = new SearchRequest(req.getIndex());
			SearchResponse res;
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			if (req.getKeyword() == null || req.getKeyword().equals("")) {
				res = client.search(searchRequest, RequestOptions.DEFAULT);
			} else {
				List<MultiMatchQueryBuilder> multiMatchQueryBuilders = new ArrayList<MultiMatchQueryBuilder>();
				for (FilterParam obj : req.getFilterParams()) {
					MultiMatchQueryBuilder q1 = QueryBuilders.multiMatchQuery(req.getKeyword(),
							new String[] { obj.getKey() });
					q1.type(q1.type().PHRASE_PREFIX);

					multiMatchQueryBuilders.add(q1);
				}

				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
				for (MultiMatchQueryBuilder obj : multiMatchQueryBuilders) {
					boolQueryBuilder.should(obj);
				}
				QueryBuilder boolQuery = boolQueryBuilder;
				sourceBuilder.query(boolQuery);

				searchRequest.source(sourceBuilder);
				res = client.search(searchRequest, RequestOptions.DEFAULT);
			}

			SearchHits hits = res.getHits();
			SearchHit[] searchHits = hits.getHits();
			Gson g = new Gson();
			for (SearchHit hit : searchHits) {
				ObjectMapper o = new ObjectMapper();
				java.util.Map<String, Object> map = hit.getSourceAsMap();
				java.util.Map<String, Object> map2 = new HashMap();
				map.forEach((k, v) -> {
					if (!k.equals("NameUtf8"))
						map2.put(Character.toLowerCase(k.charAt(0)) + k.substring(1), v);
				});
				Object e = o.readValue(g.toJson(map2), Object.class);
				result.add(e);

			}
			/* client.close(); */
			apiRes.setObject(result);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	@Override
	public ApiRes<Object> importjson(ImportJsonReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			int x = 1;
			List<Object> lstimportObject = new ObjectMapper().readValue(req.getFile().getBytes(), List.class);
			BulkRequest bulkReq = new BulkRequest();
			String index = req.getIndex();
			Gson g = new Gson();
			for (int i = 0; i < lstimportObject.size(); i++) {
				bulkReq.add(new IndexRequest(index).id(String.valueOf(i + 1)).source(g.toJson(lstimportObject.get(i)),
						XContentType.JSON));
			}
			client.bulk(bulkReq, RequestOptions.DEFAULT);

		} catch (Exception e) {

		}
		return apiRes;
	}

	@Override
	public ApiRes<Object> getindex() {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			GetIndexRequest request = new GetIndexRequest("*");
			GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
			String[] indices = response.getIndices();
			apiRes.setObject(indices);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

}
