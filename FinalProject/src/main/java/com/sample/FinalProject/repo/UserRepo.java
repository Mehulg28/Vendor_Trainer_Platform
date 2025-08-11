package com.sample.FinalProject.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.FinalProject.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	public Optional<User> findByEmail(String email);

	public List<User> findByApproved(boolean flag);
}