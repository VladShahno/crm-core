package com.crm.verification.core.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "addresses")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"created", "updated"},
    allowGetters = true
)
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "phone_number")
  private String phoneNumber;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "company")
  private Company company;

  @Column(name = "country")
  private String country;

  @Column(name = "street")
  private String street;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "postal_code")
  private String postalCode;

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
    if (!(o instanceof Address))
      return false;
    Address address = (Address) o;
    return getCompany().equals(address.getCompany()) && getCountry().equals(address.getCountry()) &&
        getStreet().equals(address.getStreet()) && getCity().equals(address.getCity()) &&
        Objects.equals(getState(), address.getState()) &&
        Objects.equals(getPostalCode(), address.getPostalCode()) &&
        getPhoneNumber().equals(address.getPhoneNumber());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCompany(), getCountry(), getStreet(), getCity(), getState(), getPostalCode(),
        getPhoneNumber());
  }

  @Override
  public String toString() {
    return "Address{" +
        "id=" + id +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", company=" + company +
        ", country='" + country + '\'' +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", postalCode='" + postalCode + '\'' +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}
