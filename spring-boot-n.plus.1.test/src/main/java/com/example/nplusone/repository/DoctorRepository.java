package com.example.nplusone.repository;

import com.example.nplusone.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
//  List<Tutorial> findByPublished(boolean published);
//
//  List<Tutorial> findByTitleContaining(String title);
}
