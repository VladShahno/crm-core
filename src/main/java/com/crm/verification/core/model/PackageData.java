package com.crm.verification.core.model;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "packages")
@Data
@NoArgsConstructor
public class PackageData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "package_name", nullable = false)
  private String packageName;

  @Column(name = "package_id", nullable = false)
  private String packageId;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "package_leads")
  private List<Lead> leads;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created")
  private Date created;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated")
  private Date updated;
}
