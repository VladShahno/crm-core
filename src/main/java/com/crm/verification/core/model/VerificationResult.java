package com.crm.verification.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verification_result")
@Getter
@Setter
@RequiredArgsConstructor
public class VerificationResult {

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne
  @JoinColumn(name = "email")
  @JsonIgnore
  Lead lead;

  @ManyToOne
  @JoinColumn(name = "package_id")
  @JsonIgnore
  PackageData packageData;

  String result;
}
