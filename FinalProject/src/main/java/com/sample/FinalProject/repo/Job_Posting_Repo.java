package com.sample.FinalProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.FinalProject.model.Job_Posting;

@Repository
public interface Job_Posting_Repo extends JpaRepository<Job_Posting, Integer> {

}
