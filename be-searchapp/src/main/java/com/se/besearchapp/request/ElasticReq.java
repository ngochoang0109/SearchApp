package com.se.besearchapp.request;

import java.util.List;

public class ElasticReq {
	private String Index;
	private List<Object> dataSource;

	public String getIndex() {
		return Index;
	}

	public void setIndex(String index) {
		Index = index;
	}

	public List<Object> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<Object> dataSource) {
		this.dataSource = dataSource;
	}

}
