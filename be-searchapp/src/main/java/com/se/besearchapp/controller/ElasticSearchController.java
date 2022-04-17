
package com.se.besearchapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.se.besearchapp.request.ElasticReq;
import com.se.besearchapp.service.impl.ElasticSearchServiceImpl;

@RestController

@RequestMapping("/api/elastic")
public class ElasticSearchController {

	@Autowired
	private ElasticSearchServiceImpl objService;
	/*
	 * @RequestMapping(value = "/addindex", method = RequestMethod.POST, consumes =
	 * "application/json", produces = "application/json; charset=utf-8") public
	 * ResponseEntity<Object> getList(@RequestBody ElasticReq req) throws Exception
	 * { return ResponseEntity.ok(objService.addIndex(req)); }
	 */

	@RequestMapping(value = "/addindex", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8")
	public ResponseEntity<Object> getList(@RequestBody ElasticReq req) throws Exception {
		return ResponseEntity.ok(objService.addIndex(req));
	}
}
