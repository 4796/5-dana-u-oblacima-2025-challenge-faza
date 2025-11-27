package com.example.menze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.menze.model.Student;
import com.example.menze.repo.StudentRepository;

import jakarta.validation.Valid;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public Student findById(String id1) {
		try {
			Long id = Long.valueOf(id1);
			return studentRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid student ID format");
		}
	}
	
	public Student create(@Valid Student request) {
		try {
			return studentRepository.save(request);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating student");
		}
	}
}
