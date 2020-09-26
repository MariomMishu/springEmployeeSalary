package com.spring5.practice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring5.practice.model.BasicSalary;
import com.spring5.practice.model.Grade;
import com.spring5.practice.service.BasicSalaryService;


// For showing index.jsp instead of showing the "not found page" error
@Controller
public class RootController {
	
	@Autowired
	private BasicSalaryService basicSalaryService;

	
	@GetMapping("/")
	public String root() {
	//	generateGrades() ;
		return "index";
	}
	@GetMapping("/basicSalary/add")
	public String add_GET(Model model) {
		model.addAttribute("basicSalary", new BasicSalary());
		model.addAttribute("message", "Please add Basic salary");
		return "basicSalary/add";
	}
	@PostMapping("/basicSalary/update")
	public String add_POST(Model model, @ModelAttribute(name = "bs") BasicSalary bs) {
		basicSalaryService.add(bs);
		model.addAttribute("message", "Basic added successfully");
		return "redirect:/";
	}
	//private static List<Grade> grades = new ArrayList<Grade>();

	/*
	 * private void generateGrades() { // gradeService.all_delete();
	 * //for(i=1;i<=6;i++) {
	 * 
	 * if (grades.stream().filter(grade ->
	 * grade.getGrade().equals("Grade One")).findAny().isEmpty()) { var g = new
	 * Grade(); g.setGrade("Grade One"); gradeService.add(g);
	 * 
	 * } if (grades.stream().filter(grade ->
	 * grade.getGrade().equals("Grade Two")).findAny().isEmpty()) { var g = new
	 * Grade(); g.setGrade("Grade Two"); gradeService.add(g);
	 * 
	 * } if (grades.stream().filter(grade ->
	 * grade.getGrade().equals("Grade Three")).findAny().isEmpty()) { var g = new
	 * Grade(); g.setGrade("Grade Three"); gradeService.add(g);
	 * 
	 * } //}
	 * 
	 * }
	 * 
	 */	
}
