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
import com.se.besearchapp.request.ElasticDatasourceReq;
import com.se.besearchapp.request.FilterReq;
import com.se.besearchapp.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	private RestHighLevelClient client;

	public ApiRes<Object> saveMulArticle(ArticleElasticReq req) {

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

	@Override
	public ApiRes<Object> saveSingleArticle(ElasticDatasourceReq req) {

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
	public ApiRes<Object> deleteSingleArticle(String index, long id) {
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
	public ApiRes<Object> deleteMulArticleFromIndex(String index, List<Integer> ids) {
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

	@Override
	public ApiRes<Object> deleteAllArticleFromIndex(String index) {
		// TODO Auto-generated method stub
		return null;
	}

}
