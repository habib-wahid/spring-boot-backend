package com.usb.pss.ipaservice.admin.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "revinfo")
public class CustomRevInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@SequenceGenerator(name = "revinfo_gen", sequenceName = "revinfo_seq", allocationSize = 1)
    private Long rev;
}
