package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Map;

public interface PasswordPolicyRepository extends RevisionRepository<PasswordPolicy, Long, Long>,
    JpaRepository<PasswordPolicy, Long> {

    @Query("""
        SELECT 1 AS serial,'passwordLength' AS policy,'Minimum password length' AS description,
        p.passwordLength AS value
        FROM PasswordPolicy p
        UNION
        SELECT 2 AS serial,'containsUppercase' AS policy,'Password contains uppercase letter' AS description,
        CASE WHEN p.containsUppercase THEN 1 ELSE 0 END AS value
        FROM PasswordPolicy p
        UNION
        SELECT 3 AS serial,'containsLowercase' AS policy,'Password contains lowercase letter' AS description,
        CASE WHEN p.containsLowercase THEN 1 ELSE 0 END AS value
        FROM PasswordPolicy p
        UNION
        SELECT 4 AS serial,'containsDigit' AS policy, 'Password contains digit' AS description,
        CASE WHEN p.containsDigit THEN 1 ELSE 0 END AS value
        FROM PasswordPolicy p
        UNION
        SELECT 5 AS serial,'containsSpecialCharacters' AS policy,'Password contains special character' AS description,
        CASE WHEN p.containsSpecialCharacters THEN 1 ELSE 0 END AS value
        FROM PasswordPolicy p
        """)
    List<Map<String, Object>> getPasswordPolicies();
}
