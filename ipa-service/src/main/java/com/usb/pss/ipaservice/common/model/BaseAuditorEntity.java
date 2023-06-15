package com.usb.pss.ipaservice.common.model;

import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import jakarta.persistence.Version;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseAuditorEntity extends BaseEntity{

    /*  To-Do list:
        * Need to implement two dates
        * Server date
        * system date
     */

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by_id")
    private Long createdById;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by_id")
    private Long updatedById;

    @Column(name = "deleted_by_id")
    private Long deletedById;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Version
    private int version;

    @PrePersist
    private void onPersist(){
        createdAt = LocalDateTime.now();
        createdById = (SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken) ? null : LoggedUserHelper.getCurrentUserId();
    }
    @PreUpdate
    private void onModification() {

        if (isDeleted()) {
            if (deletedById == null) {
                deletedById = LoggedUserHelper.getCurrentUserId();
            }
            if (getDeletedAt() == null) {
                deletedAt = LocalDateTime.now();
            }
        }

        updatedAt = LocalDateTime.now();
        updatedById = LoggedUserHelper.getCurrentUserId();
    }
}
