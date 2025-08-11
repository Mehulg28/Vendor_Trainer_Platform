package com.sample.FinalProject.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.FinalProject.model.Vendor;

@Repository
public interface VendorRepo extends JpaRepository<Vendor, Integer> {
	Optional<Vendor> findByEmail(String email);

}