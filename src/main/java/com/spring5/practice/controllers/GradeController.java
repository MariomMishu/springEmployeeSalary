package com.spring5.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring5.practice.model.Grade;
import com.spring5.practice.service.GradeService;

@Controller
public class GradeController {
	@Autowired
	GradeService gradeService;

	@GetMapping("/grade/add")
	public String addCountry_GET(Model model) {
		model.addAttribute("grade", new Grade());
		model.addAttribute("message", "Please add a Grade");
		return "grade/add";
	}

	@PostMapping("/grade/add")
	public String addCountry(Model model, @ModelAttribute(name = "grade") Grade grade) {
		gradeService.add(grade);
		model.addAttribute("message", "Grade added successfully");
		return "redirect:/grade/show-all";
	}

	@GetMapping("/grade/show-all")
	public String showAll_GET(Model model) {
		model.addAttribute("grades", gradeService.getAll());
		model.addAttribute("message", "Showing all grades");
		return "grade/show-all";
	}

	
	@GetMapping("/grade/edit")
	public String edit_GET(@RequestParam("id") long id, Model model) {
		Grade obj = gradeService.getById(id);
		model.addAttribute("grade", obj);
		model.addAttribute("message", "Edit the Grade");
		return "grade/edit";
	}

	@PostMapping("/grade/edit")
	public String edit(Model model, @ModelAttribute(name = "grade") Grade grade) {
		gradeService.edit(grade);
		model.addAttribute("message", "grade Edited Successfully");
		return "redirect:/grade/show-all";
	}

	@GetMapping("/country/delete")
	public String delete_GET(Model model, @RequestParam("id") long id) {
		gradeService.delete(id);
		model.addAttribute("message","Grade deleted successfully");
		return "redirect:/grade/show-all";
	}
}
