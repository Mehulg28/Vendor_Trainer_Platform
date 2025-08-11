package com.sample.FinalProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.FinalProject.model.*;
import com.sample.FinalProject.repo.*;

@Service
public class UserService {
	@Autowired
	UserRepo user_repo;	
	public Optional<User> findByEmail(String email){
		return user_repo.findByEmail(email);
	}
	
	public Optional<User> login(String email, String password) {
		Optional<User> user = user_repo.findByEmail(email);
		if(user.isEmpty()) {
			return Optional.empty();
		}
		else {
			String original_password = user.get().getPassword();
			if(!password.equals(original_password)) {
				return Optional.empty();
			}
			return user;
		}
	}
	
	public boolean signup(User user) {
		try {
			user_repo.save(user);
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}

	public void save(User user) {
		user_repo.save(user);
	}
	
	public List<User> findByApproved(boolean flag){
		return user_repo.findByApproved(flag);
	}

	public User findById(int id) {
		return user_repo.findById(id).get();
	}
}
