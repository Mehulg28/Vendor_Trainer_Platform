package com.sample.FinalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.FinalProject.model.Job_Posting;
import com.sample.FinalProject.model.Vendor;
import com.sample.FinalProject.service.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/vendor/")
public class VendorController {
	@Autowired
	VendorService vendor_serv;
	
	@Autowired
	SkillService skill_serv;
	
	@Autowired
	JobPostService job_serv;
	
    @Value("${file.upload-dir}")
    private String uploadDir;

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		Vendor session_vendor = (Vendor) session.getAttribute("vendor");
		Vendor vendor = vendor_serv.findById(session_vendor.getId()).get();
		model.addAttribute("vendor", vendor);
		return "vendor_dashboard";
	}	
	
	@GetMapping("/dashboard/job-post")
	public String job_posting(Model model, HttpSession session) {
		model.addAttribute("job_post", new Job_Posting());
		model.addAttribute("skillList", skill_serv.getAll());
		return "job_posting_form";
	}	
	
	@PostMapping("/dashboard/job-post")
	public String job_posting(Job_Posting job_post, Model model, HttpSession session) {
		Vendor session_vendor = (Vendor) session.getAttribute("vendor");
		if(session_vendor == null) {
			model.addAttribute("message", "Session Expired");
			return "login_form";
		}
		job_post.setVendor(session_vendor);
		job_serv.save(job_post);
		return "redirect:/vendor/dashboard";
	}	
}