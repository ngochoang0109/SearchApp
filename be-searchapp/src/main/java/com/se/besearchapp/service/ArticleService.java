package com.se.besearchapp.service;

import java.util.List;

import com.se.besearchapp.helpers.ApiRes;
import com.se.besearchapp.request.ArticleElasticReq;
import com.se.besearchapp.request.ElasticDatasourceReq;
import com.se.besearchapp.request.ElasticReq;
import com.se.besearchapp.request.FilterReq;

public interface ArticleService {
	ApiRes<Object> saveMulArticle(ArticleElasticReq req);

	ApiRes<Object> saveSingleArticle(ElasticDatasourceReq req);

	ApiRes<Object> deleteSingleArticle(String index, long id);

	ApiRes<Object> deleteMulArticleFromIndex(String index, List<Integer> ids);

	ApiRes<Object> deleteAllArticleFromIndex(String index);

	ApiRes<Object> search(FilterReq req);

}
