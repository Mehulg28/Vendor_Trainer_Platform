package com.sample.FinalProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.FinalProject.model.Job_Posting;
import com.sample.FinalProject.repo.Job_Posting_Repo;

@Service
public class JobPostService {
	@Autowired
	private Job_Posting_Repo repo;
	
	public List<Job_Posting> findByAll(){
		return repo.findAll();
	}

	public void save(Job_Posting job_post) {
		repo.save(job_post);
	}

	public Optional<Job_Posting> findById(int job_id) {
		return repo.findById(job_id);
	}
}	
