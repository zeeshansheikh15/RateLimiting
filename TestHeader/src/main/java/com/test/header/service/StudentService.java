package com.test.header.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.header.entity.Student;
import com.test.header.repository.StudentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository stdRepo;
	
	public Mono<Student> addStudent(Student std) {
		 Mono<Student> stdAdded = stdRepo.save(std);
		 return stdAdded;
	}

	public Flux<Student> getStudents() {
		Flux<Student> list = stdRepo.findAll();
		return list;
	}
}
