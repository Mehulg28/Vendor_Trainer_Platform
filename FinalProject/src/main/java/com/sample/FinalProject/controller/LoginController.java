package com.sample.FinalProject.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
@RequestMapping("/")
public class LoginController {
	@Autowired
	private UserService user_serv;
	
	@Autowired
	private VendorService vendor_serv;
	
	@Autowired
	private TrainerService train_serv;
	
	@Autowired
	private SkillService skill_serv;
	
    @Value("${file.upload-dir}")
    private String uploadDir;
	
	@GetMapping("/")
	public String login() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		session.invalidate();
		return "login_form";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
		Optional<User> user = user_serv.login(email, password);
		if(!user.isEmpty()) {
			User ver_user = user.get();
			String role = ver_user.getRole();
			if(role.equals("Admin")) {
				Trainer trainer = train_serv.findByEmail(ver_user.getEmail()).get();
				session.setAttribute("trainer", trainer);
				return "redirect:/admin/dashboard";
			}
			else if(role.equals("Trainer")) {
				Trainer trainer = train_serv.findByEmail(ver_user.getEmail()).get();
				session.setAttribute("trainer", trainer);
				return "redirect:/trainer/dashboard";
			}
			else {
				Vendor vendor = vendor_serv.findByEmail(ver_user.getEmail()).get();
				session.setAttribute("vendor", vendor);
				return "redirect:/vendor/dashboard";
			}
		}
		else
		{
			model.addAttribute("message", "Invalid Credentials");
			return "login_form";
		}
	}
	
	@GetMapping("/signup/vendor")
	public String signup_vendor(Model model, HttpSession session) {
		session.invalidate();
		Vendor vendor = new Vendor();
		model.addAttribute("vendor", vendor);
        return "vendor_signup";
	}
	
	@GetMapping("/signup/trainer")
	public String signup_trainer(Model model, HttpSession session) {
		session.invalidate();
		Trainer trainer = new Trainer();
		model.addAttribute("trainer", trainer);
		model.addAttribute("skillList", skill_serv.getAll());
        return "trainer_signup";
	}
	
	@PostMapping("/signup/vendor")
	public String signup_vendor(HttpSession session, Vendor vendor, @RequestParam MultipartFile photoFile, @RequestParam MultipartFile id_card_File, Model model) {
        try 
        {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String photoFilename = UUID.randomUUID() + "_" + photoFile.getOriginalFilename();
            File photoDest = new File(uploadDir + File.separator + photoFilename);
            photoFile.transferTo(photoDest);
            vendor.setPhoto("/uploads/" + photoFilename);

            String idCardFilename = UUID.randomUUID() + "_" + id_card_File.getOriginalFilename();
            File idCardDest = new File(uploadDir + File.separator + idCardFilename);
            id_card_File.transferTo(idCardDest);
            vendor.setId_card("/uploads/" + idCardFilename);
        } 
        catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e);
            return "vendor_signup";
        }
        Optional <Vendor> get_vendor = vendor_serv.findByEmail(vendor.getEmail());
        if(get_vendor.isEmpty()) {
    		vendor.setRole("Vendor");
        	vendor_serv.save(vendor);
        	session.setAttribute("vendor", vendor);
        	return "redirect:/vendor/dashboard";
        }
        else{
        	String role = get_vendor.get().getRole();
            model.addAttribute("message", "You have already registered as " + role + ". Please login to continue");
            return "login_form";
        }
	}
	
	@PostMapping("/signup/trainer")
	public String signup_trainer(HttpSession session, Trainer trainer, @RequestParam MultipartFile photoFile, @RequestParam MultipartFile id_card_File, Model model) {
        try 
        {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String photoFilename = UUID.randomUUID() + "_" + photoFile.getOriginalFilename();
            File photoDest = new File(uploadDir + File.separator + photoFilename);
            photoFile.transferTo(photoDest);
            trainer.setPhoto("/uploads/" + photoFilename);

            String idCardFilename = UUID.randomUUID() + "_" + id_card_File.getOriginalFilename();
            File idCardDest = new File(uploadDir + File.separator + idCardFilename);
            id_card_File.transferTo(idCardDest);
            trainer.setId_card("/uploads/" + idCardFilename);
        } 
        catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e);
            return "trainer_signup";
        }
        Optional <Trainer> get_trainer = train_serv.findByEmail(trainer.getEmail());
        if(get_trainer.isEmpty()) {
    		trainer.setRole("Trainer");
        	train_serv.save(trainer);
        	session.setAttribute("trainer", trainer);
        	return "redirect:/trainer/dashboard";
        }
        else{
        	String role = get_trainer.get().getRole();
            model.addAttribute("message", "You have already registered as " + role + ". Please login to continue");
            return "login_form";
        }
	}
}