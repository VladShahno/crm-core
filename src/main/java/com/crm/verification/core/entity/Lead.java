package com.crm.verification.core.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

@Entity
@Table(name = "leads")
@Getter
@Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"created", "updated"},
    allowGetters = true
)
public class Lead {

  @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL)
  Set<VerificationResult> verificationResults = new HashSet<>();

  @Id
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {
      CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
  })
  private Company company;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
  }, mappedBy = "leads")
  private Set<PackageData> packageData = new HashSet<>();

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "title")
  private String title;

  @Column(name = "proof_link")
  private String proofLink;

  @Column(name = "lead_comments")
  private String leadComments;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created", updatable = false)
  @CreatedDate
  private Date created;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated")
  @LastModifiedDate
  private Date updated;

  public void addPackage(PackageData packageData) {
    this.packageData.add(packageData);
    //lead.getPackageData().add(this);
  }

  public void removePackage(PackageData packageData) {
    this.packageData.remove(packageData);
    packageData.getLeads().remove(this);
  }

  public void addVerificationResult(VerificationResult verificationResult) {
    this.verificationResults.add(verificationResult);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Lead))
      return false;
    Lead lead = (Lead) o;
    return getEmail().equals(lead.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmail());
  }

  @Override
  public String toString() {
    return "Lead{" +
        "verificationResults=" + verificationResults +
        ", email='" + email + '\'' +
        ", company=" + company +
        ", packageData=" + packageData +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", title='" + title + '\'' +
        ", proofLink='" + proofLink + '\'' +
        ", leadComments='" + leadComments + '\'' +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}
