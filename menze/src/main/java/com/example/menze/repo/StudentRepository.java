package com.example.menze.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.menze.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	boolean existsByEmail(String email);
}
