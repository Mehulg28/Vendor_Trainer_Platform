package com.sample.FinalProject.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.FinalProject.model.Trainer;

@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Integer> {
	Optional<Trainer> findByEmail(String email);

}
