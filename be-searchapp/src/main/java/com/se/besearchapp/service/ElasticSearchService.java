package com.se.besearchapp.service;

import java.util.List;

import com.se.besearchapp.helpers.ApiRes;
import com.se.besearchapp.request.DataSourceReq;
import com.se.besearchapp.request.ElasticDatasourceReq;
import com.se.besearchapp.request.ElasticReq;
import com.se.besearchapp.request.FilterReq;

public interface ElasticSearchService {
	ApiRes<Object> saveMulDatasource(ElasticReq req);

	ApiRes<Object> saveSingleDatasource(ElasticDatasourceReq req);

	ApiRes<Object> deleteSingleDatasource(String index, long id);

	ApiRes<Object> deleteMulDatasourceFromIndex(String index, List<Integer> ids);

	ApiRes<Object> deleteAllDatasourceFromIndex(String index);

	ApiRes<Object> search(FilterReq req);

}
