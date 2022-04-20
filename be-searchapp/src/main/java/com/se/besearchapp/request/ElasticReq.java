package com.se.besearchapp.request;

import java.util.List;

import com.se.besearchapp.pojo.DataSource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticReq {
	private String Index;
	private List<DataSource> dataSource;
}
