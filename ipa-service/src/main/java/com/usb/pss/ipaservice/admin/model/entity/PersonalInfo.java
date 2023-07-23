package com.usb.pss.ipaservice.admin.model.entity;

import com.usb.pss.ipaservice.common.model.BaseAuditorEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adm_personal_info")
public class PersonalInfo extends BaseAuditorEntity {
    private String firstName;
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY)
    private Designation designation;
    private String emailOfficial;
    private String emailOther;
    private String mobileNumber;
    private String telephoneNumber;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "adm_personal_info_point_of_sales_mapping",
        joinColumns = @JoinColumn(name = "personal_info_id"),
        inverseJoinColumns = @JoinColumn(name = "point_of_sales_id"))
    private Set<PointOfSale> pointOfSales;
    private String accessLevel;
    private String airport;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "adm_personal_info_currency_mapping",
        joinColumns = @JoinColumn(name = "personal_info_id"),
        inverseJoinColumns = @JoinColumn(name = "currency_id"))
    private Set<Currency> allowedCurrencies;
}
