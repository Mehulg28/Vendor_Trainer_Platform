package com.sample.FinalProject.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DiscriminatorValue("VENDOR")
@Table(name = "vendors")
public class Vendor extends User{
    private String company_name;
    private String company_address;
    private String company_city;
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job_Posting> job_postings;
}