package com.sample.FinalProject.model;

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
@DiscriminatorValue("TRAINER")
@Table(name = "trainers")
public class Trainer extends User{
	private String location;
	
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "json")
	private List<String> skills;


    @ManyToMany
    @JoinTable(
        name = "job_applications",
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    private Set<Job_Posting> applications = new HashSet<>();
}