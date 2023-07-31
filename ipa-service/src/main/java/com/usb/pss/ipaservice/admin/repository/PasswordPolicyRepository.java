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
        SELECT 'passwordLength' AS Policy,'Minimum password length' AS Description, p.passwordLength AS Value
        FROM PasswordPolicy p
        UNION
        SELECT 'containsUppercase' AS Policy,'Password contains uppercase letter' AS Description,
        CASE WHEN p.containsUppercase THEN 1 ELSE 0 END AS Value
        FROM PasswordPolicy p
        UNION
        SELECT 'containsLowercase' AS Property,'Password contains lowercase letter' AS Description,
        CASE WHEN p.containsLowercase THEN 1 ELSE 0 END AS Value
        FROM PasswordPolicy p
        UNION
        SELECT 'containsDigit' AS Policy, 'Password contains digit' AS Description,
        CASE WHEN p.containsDigit THEN 1 ELSE 0 END AS Value
        FROM PasswordPolicy p
        UNION
        SELECT 'containsSpecialCharacters' AS Policy,'Password contains special character' AS Description,
        CASE WHEN p.containsSpecialCharacters THEN 1 ELSE 0 END AS Value
        FROM PasswordPolicy p
        """)
    List<Map<String, Object>> getPasswordPolicies();
}
