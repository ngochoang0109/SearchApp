package com.se.besearchapp.service.impl;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.se.besearchapp.entities.Article;
import com.se.besearchapp.helpers.ApiRes;
import com.se.besearchapp.helpers.ConvertHelper;
import com.se.besearchapp.repo.ArticleRepository;
import com.se.besearchapp.request.ArticleElasticReq;
import com.se.besearchapp.request.FilterReq;

@Service
public class ArticleServiceImpl {
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	private RestHighLevelClient client;

	public ApiRes<Object> saveMulDatasource(ArticleElasticReq req) {

		ApiRes<Object> apiRes = new ApiRes<Object>();

		try {

			BulkRequest bulkReq = new BulkRequest();
			String index = "blog";
			Gson g = new Gson();

			for (Article dataSource : req.getArticles()) {
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

	public ApiRes<Object> search(FilterReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			SearchRequest searchRequest = new SearchRequest(req.getIndex());
			SearchResponse x = client.search(searchRequest, RequestOptions.DEFAULT);
			apiRes.setObject(x);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> findByAuthorsName(FilterReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			String name = ConvertHelper.getValueReq(req, "name");
			Page<Article> x = articleRepository.findByAuthorsNameUsingCustomQuery(name,
					PageRequest.of(req.getPageIndex() - 1, 10));
			apiRes.setObject(x);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> findByFilteredTagQuery(FilterReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			String tags = ConvertHelper.getValueReq(req, "tags");
			Page<Article> x = articleRepository.findByFilteredTagQuery(tags,
					PageRequest.of(req.getPageIndex() - 1, 10));
			apiRes.setObject(x);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

	public ApiRes<Object> findByAuthorsNameAndFilteredTagQuery(FilterReq req) {
		ApiRes<Object> apiRes = new ApiRes<Object>();
		try {
			String tags = ConvertHelper.getValueReq(req, "tags");
			String name = ConvertHelper.getValueReq(req, "name");
			Page<Article> x = articleRepository.findByAuthorsNameAndFilteredTagQuery(name, tags,
					PageRequest.of(req.getPageIndex() - 1, 10));
			apiRes.setObject(x);
		} catch (Exception e) {
			apiRes.setError(true);
			apiRes.setErrorReason(e.getMessage());
		}
		return apiRes;
	}

}
