package com.sample.FinalProject.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.FinalProject.model.*;
import com.sample.FinalProject.repo.*;

@Service
public class TrainerService {
	@Autowired
	private TrainerRepo trainer_repo;
	
	public Optional<Trainer> findByEmail(String email) {
		return trainer_repo.findByEmail(email);
	}

	public void save(Trainer trainer) {
		trainer_repo.save(trainer);
	}

	public Optional<Trainer> findById(int id) {
		return trainer_repo.findById(id);
	}
}
