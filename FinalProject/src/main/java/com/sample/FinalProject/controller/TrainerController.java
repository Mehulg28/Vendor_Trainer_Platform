package com.sample.FinalProject.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sample.FinalProject.model.*;
import com.sample.FinalProject.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/trainer/")
public class TrainerController {
	@Autowired
	TrainerService train_serv;
	
	@Autowired
	SkillService skill_serv;
	
	@Autowired
	JobPostService job_serv;
	
    @Value("${file.upload-dir}")
    private String uploadDir;
	
	@GetMapping("/edit")
	public String edit(HttpSession session, Model model) {
		Trainer trainer = (Trainer) session.getAttribute("trainer");
		model.addAttribute("trainer", trainer);
		model.addAttribute("skillList", skill_serv.getAll());
		return "trainer_edit";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		Trainer session_trainer = (Trainer) session.getAttribute("trainer");
		Trainer trainer = train_serv.findById(session_trainer.getId()).get();
		model.addAttribute("trainer", trainer);
		List<Job_Posting>allJobs = job_serv.findByAll();
		List<Job_Posting> jobList = allJobs.stream()
			    .filter(job -> !trainer.getApplications().contains(job))
			    .toList();
		model.addAttribute("jobList", jobList);
		return "trainer_dashboard";
	}	
	
	@PostMapping("/update")
	public String update(Trainer trainer, @RequestParam MultipartFile photoFile, Model model, HttpSession session) {
		Trainer session_trainer = (Trainer) session.getAttribute("trainer");
		if(photoFile != null) {
			try 
			{
				File dir = new File(uploadDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String photoFilename = UUID.randomUUID() + "_" + photoFile.getOriginalFilename();
				File photoDest = new File(uploadDir + File.separator + photoFilename);
				photoFile.transferTo(photoDest);
				session_trainer.setPhoto("/uploads/" + photoFilename);
			} 
			catch (IOException e) {
				model.addAttribute("message", "File upload failed: " + e);
				return "trainer_edit";
			}
		}
	    session_trainer.setName(trainer.getName());
	    session_trainer.setLocation(trainer.getLocation());
	    session_trainer.setGender(trainer.getGender());
	    session_trainer.setSkills(trainer.getSkills());
		session_trainer.setApproved(false);
    	train_serv.save(session_trainer);
    	session.setAttribute("trainer", session_trainer);
		return "redirect:/trainer/dashboard";
	}	
	
	@GetMapping("/apply/{job_id}")
	public String apply(@PathVariable int job_id, HttpSession session)
	{	
		Trainer session_trainer = (Trainer) session.getAttribute("trainer");
		Trainer trainer = train_serv.findById(session_trainer.getId()).get();
		Job_Posting job_post = job_serv.findById(job_id).get();
		trainer.getApplications().add(job_post);
		job_post.getApplicants().add(trainer);
		train_serv.save(trainer);
		session.setAttribute("trainer", trainer);
		return "redirect:/trainer/dashboard";
	}
}