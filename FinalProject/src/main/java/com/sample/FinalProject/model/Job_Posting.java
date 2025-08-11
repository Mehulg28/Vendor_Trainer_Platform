package com.sample.FinalProject.model;

import java.time.LocalDateTime;
import java.util.*;

import com.sample.FinalProject.converter.StringListConverter;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "job_posts")
public class Job_Posting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int job_id; 
		
	@ManyToOne
	@JoinColumn(name = "posted_by", nullable = false)
	private Vendor vendor;
	
	private int duration;
	private LocalDateTime posted_on = LocalDateTime.now();
	private String location;
	
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "json")
	private List<String> skills;
    
    @ManyToMany(mappedBy = "applications", cascade = CascadeType.ALL)
    private Set<Trainer> applicants = new HashSet<>();
}