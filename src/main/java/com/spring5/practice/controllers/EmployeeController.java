package com.spring5.practice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring5.practice.model.BasicSalary;
import com.spring5.practice.model.Employee;
import com.spring5.practice.service.BasicSalaryService;
import com.spring5.practice.service.EmployeeService;
import com.spring5.practice.service.GradeService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	BasicSalaryService basicSalaryService;
	@Autowired
	private GradeService gradeService;

	@GetMapping("/employee/add")
	public String add_GET(Model model) {
		model.addAttribute("employee", new Employee());
	//	model.addAttribute("grade_list", gradeService.getAll());
		
		return "employee/add";
	}
	@PostMapping("/employee/add")
	public String add(Model model, @ModelAttribute("employee") Employee employee) {
		employeeService.add(employee);
		model.addAttribute("message", "New Employee Added Successfully");
		return "redirect:/employee/show-all";
	}
	@GetMapping("/employee/show-all")
	public String show_all(Model model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("employee_list", employeeService.getAll());
		model.addAttribute("message", "Showing All Employee");
		return "employee/show-all";
	}
	@GetMapping("/employee/show-salary")
	public String show_salary(Model model) {
		model.addAttribute("employee", new Employee());
		//model.addAttribute("salary_list", basicSalaryService.getAll());
		List<BasicSalary> basic_list = new ArrayList<BasicSalary>();
		basic_list = basicSalaryService.getAll();
		//employee_list = employeeService.getAll();
		//double salary=  basicSalary(basic_list.get(0).getBasicSalary());
		//model.addAttribute("salary", basicSalary(basic_list.get(0).getBasicSalary()));
		model.addAttribute("salary", basic_list.get(0).getBasicSalary());
		model.addAttribute("employee_list", employeeService.getAll());
		
		model.addAttribute("message", "Showing All Employee");
		return "account/show-salary";
	}

	
	  public double basicSalary(double salary) { 
		  
		 double house; double mc; double basic; double total=0.00;
		  List<BasicSalary> salary_list = new ArrayList<BasicSalary>();
		  salary= salary_list.get(0).getBasicSalary();
		  //salary=40000.00;
		  List<Employee> emp_list = new ArrayList<Employee>();
		  System.out.println(emp_list.size());
		  for(int i=0; i<=emp_list.size();i++ ) {
			  if(emp_list.get(i).getGrade().equals("Six")) {
				  basic= salary;
				  house= basic*20/100;
				  mc= basic*15/100;
				  total=basic+house+mc;
			  }else if(emp_list.get(i).getGrade().equals("Five")) {
				  basic= salary+5000.00;
				  house= basic*20/100;
				  mc= basic*15/100;
				  total=basic+house+mc;
			  }else if(emp_list.get(i).getGrade().equals("Four")) {
				  basic= salary+10000.00;
				  house= basic*20/100;
				  mc= basic*15/100;
				  total=basic+house+mc;
			  }else if(emp_list.get(i).getGrade().equals("Three")) {
				  basic= salary+15000.00;
				  house= basic*20/100;
				  mc= basic*15/100;
				  total=basic+house+mc;
			  }
			  else if(emp_list.get(i).getGrade().equals("Two")) {
				  basic= salary+20000.00;
				  house= basic*20/100;
				  mc= basic*15/100;
				  total=basic+house+mc;
			  }
			  else if(emp_list.get(i).getGrade().equals("One")) {
				  basic= salary+25000.00;
				  house= basic*20/100;
				  mc= basic*15/100;
				  total=basic+house+mc;
			  }
			  else {
				  total=0.00;
			  }
		  }
		  //salary_list = basicSalaryService.getAll(); 
		  return total;
		  }
	 
	@GetMapping("/employee/edit")
	public String edit_GET(Model model, @RequestParam("id") long id) {
		Employee employee = employeeService.getById(id);
		model.addAttribute("employee",employee);
		return "employee/edit";
	}
	@PostMapping("/employee/edit")
	public String edit(Model model, @ModelAttribute(name = "employee") Employee employee) {
		employeeService.edit(employee);
		model.addAttribute("message", "Employee Edited Successfully");
		return "redirect:/employee/show-all";
	}

	@GetMapping("/employee/delete")
	public String delete_GET(Model model, @RequestParam("id") long id) {
		employeeService.delete(id);
		model.addAttribute("message","Employee deleted successfully");
		return "redirect:/employee/show-all";
	}
}
