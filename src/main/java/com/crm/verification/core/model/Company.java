package com.crm.verification.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
  private List<Lead> leads = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
  private List<Address> addresses = new ArrayList<>();

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "industry")
  private String industry;

  @Column(name = "employees")
  private String employees;

  @Column(name = "employees_proof_link")
  private String employeesProofLink;

  @Column(name = "revenue")
  private String revenue;

  @Column(name = "revenue_proof_link")
  private String revenueProofLink;

  @Column(name = "company_comments")
  private String companyComments;

  @Column(name = "created", nullable = false)
  private Date created;

  @Column(name = "updated", nullable = false)
  private Date updated;
}
