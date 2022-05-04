
package com.se.besearchapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.se.besearchapp.request.ElasticDatasourceReq;
import com.se.besearchapp.request.ElasticReq;
import com.se.besearchapp.request.FilterReq;
import com.se.besearchapp.service.ElasticSearchService;
import com.se.besearchapp.service.impl.ElasticSearchServiceImpl;

@RestController

@RequestMapping("/api/elastic")
@CrossOrigin("http://localhost:3000")
public class ElasticSearchController {

	@Autowired
	private ElasticSearchService objService;

	@RequestMapping(value = "/add-document", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> addDocuments(@RequestBody(required = false) ElasticReq req) {
		return ResponseEntity.ok(objService.saveMulDatasource(req));
	}

	@RequestMapping(value = "/edit-document/{index}/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> editDocument(@PathVariable("index") String index, @PathVariable("id") long id,
			@RequestBody(required = false) ElasticDatasourceReq req) {
		req.setIndex(index);
		req.getDataSource().setId(id);
		return ResponseEntity.ok(objService.saveSingleDatasource(req));
	}

	@RequestMapping(value = "/delete-document/{index}/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteDocument(@PathVariable("index") String index, @PathVariable("id") long id) {
		return ResponseEntity.ok(objService.deleteSingleDatasource(index, id));
	}

	@RequestMapping(value = "/delete-documents/{index}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> deleteDocuments(@PathVariable("index") String index, @RequestBody Object ids) {
		Map<String, List<Integer>> data = (Map<String, List<Integer>>) ids;
		return ResponseEntity.ok(objService.deleteMulDatasourceFromIndex(index, data.get("ids")));
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> searchs(@RequestBody(required = false) FilterReq req) {
		return ResponseEntity.ok(objService.search(req));
	}
}
