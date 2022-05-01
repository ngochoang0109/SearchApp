
package com.se.besearchapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.se.besearchapp.entities.Article;
import com.se.besearchapp.request.ArticleElasticReq;
import com.se.besearchapp.request.ArticleReq;
import com.se.besearchapp.request.ElasticDatasourceReq;
import com.se.besearchapp.request.ElasticReq;
import com.se.besearchapp.request.FilterReq;
import com.se.besearchapp.service.ElasticSearchService;
import com.se.besearchapp.service.impl.ArticleServiceImpl;
import com.se.besearchapp.service.impl.ElasticSearchServiceImpl;

@RestController

@RequestMapping("/api/article")
public class ArticleController {

	@Autowired
	private ArticleServiceImpl objService;

	@RequestMapping(value = "/add-document", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> addDocuments(@RequestBody(required = false) ArticleElasticReq req) {
		return ResponseEntity.ok(objService.saveMulDatasource(req));
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> searchs(@RequestBody(required = false) FilterReq req) {
		return ResponseEntity.ok(objService.search(req));
	}

	@RequestMapping(value = "/findbyauthor", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> finbyAuthor(@RequestBody(required = false) FilterReq req) {
		return ResponseEntity.ok(objService.findByAuthorsName(req));
	}

	@RequestMapping(value = "/findbytag", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> findByFilteredTagQuery(@RequestBody(required = false) FilterReq req) {
		return ResponseEntity.ok(objService.findByFilteredTagQuery(req));
	}

	@RequestMapping(value = "/findbyauthorandtag", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> findByAuthorsNameAndFilteredTagQuery(@RequestBody(required = false) FilterReq req) {
		return ResponseEntity.ok(objService.findByAuthorsNameAndFilteredTagQuery(req));
	}
}
