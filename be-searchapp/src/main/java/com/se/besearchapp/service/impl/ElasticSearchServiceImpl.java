package com.se.besearchapp.service.impl;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.se.besearchapp.helpers.ApiRes;
import com.se.besearchapp.pojo.DataSource;
import com.se.besearchapp.request.ElasticDatasourceReq;
import com.se.besearchapp.request.ElasticReq;
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
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}

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
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}

		return apiRes;
	}

	@Override
	public ApiRes<Object> deleteSingleDatasource(String index, long id) {
		ApiRes<Object> apiRes = new ApiRes<Object>();

		try {
			client.delete(new DeleteRequest(index, String.valueOf(id)), RequestOptions.DEFAULT);

		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
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
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
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

}
