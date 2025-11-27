package com.example.menze.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.menze.model.Canteen;

public interface CanteenRepository extends JpaRepository<Canteen, Long> {
	boolean existsByName(String name);
	List<Canteen> findByName(String name);
}
