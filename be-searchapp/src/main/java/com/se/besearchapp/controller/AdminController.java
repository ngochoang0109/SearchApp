package com.se.besearchapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping(value = { "/home", "/" })
	public String loginPage(Model model) {
		return "admin/homepage";
	}

	@GetMapping("/index")
	public String quanLyTaiKhoan(Model model) {
		return "admin/indexes";
	}

	@GetMapping("/search")
	public String Search(Model model) {
		return "admin/search";
	}

}
