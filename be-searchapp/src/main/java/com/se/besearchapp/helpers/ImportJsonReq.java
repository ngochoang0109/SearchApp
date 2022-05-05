package com.se.besearchapp.helpers;

import org.springframework.web.multipart.MultipartFile;

public class ImportJsonReq {
	private String Index;
	private MultipartFile file;

	public String getIndex() {
		return Index;
	}

	public void setIndex(String index) {
		Index = index;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
