package com.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.models.LocalStats;
import com.main.services.CoronaVirusDataService;

@Controller
public class MainController {
	
	@Autowired
	private CoronaVirusDataService service;
	
	@GetMapping("/")
	public String home(Model m) {
		List <LocalStats> allStats = service.getAllStats();
		Long totalCases = allStats.stream().mapToLong(stat -> stat.getLatestCases()).sum();
		m.addAttribute("data", service.getAllStats());
		m.addAttribute("total", totalCases);
		return "index";
	}
}
