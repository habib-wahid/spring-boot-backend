package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;




public interface DesignationRepository extends JpaRepository<Designation, Long> {


    Boolean existsByName(String designationName);


}
