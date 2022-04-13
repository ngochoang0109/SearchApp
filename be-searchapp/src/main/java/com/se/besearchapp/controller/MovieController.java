package com.se.besearchapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.besearchapp.pojo.Movie;
import com.se.besearchapp.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@PostMapping
	public ResponseEntity<?> addMovie() {
		Movie movie= new Movie("1","Test 1","test 1 11 1 1 1 1 1",Double.valueOf(2000));
		return new ResponseEntity<>(movieService.addMovieIndex(movie), HttpStatus.OK);
	}
}
