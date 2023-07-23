package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Department;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

    @EntityGraph("department")
    Optional<Designation> findDesignationWithDepartmentById(Long id);

    Boolean existsByNameAndDepartment(String designationName, Department department);

    Optional<Designation> findByIdAndDepartment(Long id, Department department);

    List<Designation> findAllByDepartment(Department department);
}
