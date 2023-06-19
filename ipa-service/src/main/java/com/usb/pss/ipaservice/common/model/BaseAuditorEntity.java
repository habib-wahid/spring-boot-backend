package com.usb.pss.ipaservice.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseAuditorEntity extends BaseEntity {

    /*  To-Do list:
     * Need to implement two dates
     * Server date
     * system date
     */

    @CreatedDate
    @Column(name = "created_at", updatable = false)
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
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Version
    private int version;

    @PrePersist
    private void onPersist() {
        createdAt = LocalDateTime.now();
        if (LoggedUserHelper.getCurrentUserId().isPresent())
            createdById = LoggedUserHelper.getCurrentUserId().get();
    }

    @PreUpdate
    private void onModification() {
        updatedAt = LocalDateTime.now();
        if (LoggedUserHelper.getCurrentUserId().isPresent())
            updatedById = LoggedUserHelper.getCurrentUserId().get();

    }
}
