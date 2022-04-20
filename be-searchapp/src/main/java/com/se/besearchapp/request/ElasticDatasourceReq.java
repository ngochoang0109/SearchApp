package com.se.besearchapp.request;

import com.se.besearchapp.pojo.DataSource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticDatasourceReq {
	private String index;
	private DataSource dataSource;
}
