package com.sample.FinalProject.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.FinalProject.model.Skill;
import com.sample.FinalProject.repo.SkillRepo;

@Service
public class SkillService {
	@Autowired
	private SkillRepo repo;
	
	public List<Skill> getAll(){
		List<Skill> skillList = repo.findAll();
		skillList.sort(Comparator.comparing(Skill::getName));
		return skillList;
	}
	
	public void save(Skill skill){
		repo.save(skill);
	}
}
