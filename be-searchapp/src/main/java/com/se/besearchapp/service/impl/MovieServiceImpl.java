/*
 * package com.se.besearchapp.service.impl;
 * 
 * 
 * import java.util.Map;
 * 
 * import org.elasticsearch.action.index.IndexRequest; import
 * org.elasticsearch.action.index.IndexResponse; import
 * org.elasticsearch.client.RestHighLevelClient; import
 * org.springframework.beans.factory.annotation.Autowired;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import com.google.gson.Gson; import com.se.besearchapp.pojo.Movie; import
 * com.se.besearchapp.service.MovieService;
 * 
 * 
 * @Service public class MovieServiceImpl implements MovieService{
 * 
 * @Autowired private RestHighLevelClient client;
 * 
 * 
 * @Override public String addMovieIndex(Movie movie) { Gson g = new Gson();
 * Map<String, Object> map = (java.util.Map<String, Object>)
 * g.fromJson(g.toJson(movie), Object.class); IndexRequest indexRequest = new
 * IndexRequest("posts") .id("1").source(jsonMap); }
 * 
 * }
 */