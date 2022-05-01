package com.se.besearchapp.request;

import java.util.List;

public class FilterReq {
	private String Index;
	private String Keyword;
	private Integer PageIndex;
	private Integer PageSize;
	private List<FilterParam> FilterParams;

	public List<FilterParam> getFilterParams() {
		return FilterParams;
	}

	public String getIndex() {
		return Index;
	}

	public void setIndex(String index) {
		Index = index;
	}

	public void setFilterParams(List<FilterParam> filterParams) {
		FilterParams = filterParams;
	}

	public String getKeyword() {
		return Keyword;
	}

	public void setKeyword(String keyword) {
		Keyword = keyword;
	}

	public Integer getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		PageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return PageSize;
	}

	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
}
