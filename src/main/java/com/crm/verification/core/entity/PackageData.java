package com.crm.verification.core.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "packages")
@Getter
@Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"created", "updated"},
    allowGetters = true
)
public class PackageData {

  @OneToMany(mappedBy = "packageData", cascade = CascadeType.ALL)
  Set<VerificationResult> verificationResults = new HashSet<>();
  @Id
  @Column(name = "package_name", nullable = false, unique = true)
  private String packageName;
  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Lead> leads = new HashSet<>();

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created", updatable = false)
  @CreatedDate
  private Date created;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated")
  @LastModifiedDate
  private Date updated;

  public void addLeads(Lead lead) {
    this.leads.add(lead);
    //lead.getPackageData().add(this);
  }

  public void removeLead(Lead lead) {
    this.leads.remove(lead);
    lead.getPackageData().remove(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof PackageData))
      return false;
    PackageData that = (PackageData) o;
    return getPackageName().equals(that.getPackageName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPackageName());
  }

  @Override
  public String toString() {
    return "PackageData{" +
        "verificationResults=" + verificationResults +
        ", packageName='" + packageName + '\'' +
        ", leads=" + leads +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}
