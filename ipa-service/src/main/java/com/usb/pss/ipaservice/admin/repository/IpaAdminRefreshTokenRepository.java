package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRefreshToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IpaAdminRefreshTokenRepository extends JpaRepository<IpaAdminRefreshToken, UUID> {
    @EntityGraph(attributePaths = {"user"})
    Optional<IpaAdminRefreshToken> findByToken(UUID token);
}
