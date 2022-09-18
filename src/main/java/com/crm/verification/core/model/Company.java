package com.crm.verification.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "companies")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"created", "updated"},
    allowGetters = true
)
public class Company {

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
  private Set<Lead> leads = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
  private Set<Address> addresses = new HashSet<>();

  @Id
  @Column(name = "name", unique = true)
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

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created", updatable = false)
  @CreatedDate
  private Date created;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated")
  @LastModifiedDate
  private Date updated;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Company))
      return false;
    Company company = (Company) o;
    return getName().equals(company.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }
}
