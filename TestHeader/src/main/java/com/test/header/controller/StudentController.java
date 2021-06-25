package com.test.header.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.header.entity.Student;
import com.test.header.service.StudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StudentController {
	
	@Autowired
	private StudentService stdService;

	@PostMapping("std")
	public Mono<Student> addStd(@RequestBody Student stdPayload) {
		
		Mono<Student> addStudent = stdService.addStudent(stdPayload);
		return addStudent;
	}
	
	@GetMapping("std")
	public Flux<Student> getStudents() {
		System.out.println("2nd called "+new Date());
		Flux<Student> list = stdService.getStudents();
		return list;
	}
	
	@GetMapping("list")
	public Flux<Student> getStudentList() {
		System.out.println("2nd called "+new Date());
		Flux<Student> list = stdService.getStudents();
		return list;
	}
	
	@GetMapping("std-list")
	public Flux<Student> getStudentsList() {
		System.out.println("2nd called "+new Date());
		Flux<Student> list = stdService.getStudents();
		return list;
	}
}
