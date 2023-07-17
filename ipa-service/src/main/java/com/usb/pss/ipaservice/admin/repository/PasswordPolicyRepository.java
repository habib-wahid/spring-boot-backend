package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PasswordPolicyRepository extends JpaRepository<PasswordPolicy, Long> {

    @Query("SELECT 'passwordLength' AS Policy, p.passwordLength AS Value\n" +
        "FROM PasswordPolicy p \n" +
        "UNION \n" +
        "SELECT 'containsUppercase' AS Policy, CASE WHEN p.containsUppercase THEN 1 ELSE 0 END AS Value\n" +
        "FROM PasswordPolicy p \n" +
        "UNION  \n" +
        "SELECT 'containsLowercase' AS Property,CASE WHEN p.containsLowercase THEN 1 ELSE 0 END AS Value \n" +
        "FROM PasswordPolicy p \n" +
        "UNION \n" +
        "SELECT 'containsDigit' AS Policy, CASE WHEN p.containsDigit THEN 1 ELSE 0 END AS Value\n" +
        "FROM PasswordPolicy p \n" +
        "UNION \n" +
        "SELECT 'containsSpecialCharacters' AS Policy," +
        "CASE WHEN p.containsSpecialCharacters THEN 1 ELSE 0 END AS Value \n" +
        "FROM PasswordPolicy p")
    List<Map<String, Object>> getPasswordPolicies();
}
