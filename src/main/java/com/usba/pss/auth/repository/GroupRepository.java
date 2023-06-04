package com.usba.pss.auth.repository;

import com.usba.pss.auth.model.RolesPermissions.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Long> {
}
