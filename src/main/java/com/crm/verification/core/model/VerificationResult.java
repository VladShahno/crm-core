package com.crm.verification.core.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "verification_result")
@Getter
@Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"created", "updated"},
    allowGetters = true
)
public class VerificationResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne
  @JoinColumn(name = "email")
  Lead lead;

  @ManyToOne
  @JoinColumn(name = "package_name")
  PackageData packageData;

  @NotBlank
  String result;

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
    if (!(o instanceof VerificationResult))
      return false;
    VerificationResult that = (VerificationResult) o;
    return getId().equals(that.getId()) && getLead().equals(that.getLead()) &&
        getPackageData().equals(that.getPackageData()) && getResult().equals(that.getResult());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getLead(), getPackageData(), getResult());
  }
}
