package com.sample.FinalProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.FinalProject.model.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Integer> {

}
