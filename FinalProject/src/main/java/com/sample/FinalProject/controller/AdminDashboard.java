package com.sample.FinalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sample.FinalProject.model.Skill;
import com.sample.FinalProject.model.User;
import com.sample.FinalProject.service.*;

@Controller
@RequestMapping("/admin")
public class AdminDashboard {
	@Autowired
	UserService user_serv;
	
	@Autowired
	SkillService skill_serv;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("pendingList", user_serv.findByApproved(false));
		model.addAttribute("skillList", skill_serv.getAll());
		return "admin_dashboard";
	}
	
	@GetMapping("approve/{id}")
	public String dashboard(@PathVariable int id, Model model) {
		User user = user_serv.findById(id);
		user.setApproved(true);
		user_serv.save(user);
		return "redirect:/admin/dashboard";
	}
	
	@PostMapping("/skills/add")
	public String dashboard(@RequestParam String skillname, Model model) {
		Skill skill = new Skill();
		skill.setName(skillname);
		skill_serv.save(skill);
		return "redirect:/admin/dashboard";
	}
}
