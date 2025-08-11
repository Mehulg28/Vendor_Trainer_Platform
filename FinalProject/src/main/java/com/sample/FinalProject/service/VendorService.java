package com.sample.FinalProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.FinalProject.model.*;
import com.sample.FinalProject.repo.*;

@Service
public class VendorService {
	@Autowired
	private VendorRepo vendor_repo;
	
	public void signup(Vendor vendor, User user) {
		vendor_repo.save(vendor);
	}

	public Optional<Vendor> findByEmail(String email) {
		return vendor_repo.findByEmail(email);
	}

	public void save(Vendor vendor) {
		vendor_repo.save(vendor);
	}

	public Optional<Vendor> findById(int id) {
		return vendor_repo.findById(id);
	}
}
