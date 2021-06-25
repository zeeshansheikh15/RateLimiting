package com.test.header.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.test.header.entity.Student;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, String> {

}
