package com.greatlearning.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.student.entity.Student;
import com.greatlearning.student.service.StudentService;



@Controller
@RequestMapping("/Students")
public class StudentsController {

	@Autowired
	private StudentService StudentService;


	@RequestMapping("/list")
	public String listStudents(Model theModel) {

		
		List<Student> theStudents = StudentService.findAll();

		theModel.addAttribute("Students", theStudents);

		return "list-Students";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		
		Student theStudent = new Student();

		theModel.addAttribute("Student", theStudent);

		return "Student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("StudentId") int theId, Model theModel) {

		
		Student theStudent = StudentService.findById(theId);

		
		theModel.addAttribute("Student", theStudent);

		
		return "Student-form";
	}

	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) {

		System.out.println(id);
		Student theStudent;
		if (id != 0) {
			theStudent = StudentService.findById(id);
			theStudent.setName(name);
			theStudent.setDepartment(department);
			theStudent.setCountry(country);
		} else
			theStudent = new Student(name, department, country);
		
		StudentService.save(theStudent);

		
		return "redirect:/Students/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("StudentId") int theId) {

		
		StudentService.deleteById(theId);

		
		return "redirect:/Students/list";

	}

}
