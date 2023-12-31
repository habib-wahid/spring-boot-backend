package com.usb.pss.ipaservice.common.model;

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

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseAuditorEntity extends BaseEntity {

    //ToDo list: Need to implement two dates Server date and system date

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    private Long updatedBy;
    private LocalDateTime updatedDate;

    @Version
    private int version;

    @PrePersist
    private void onPersist() {
        createdDate = LocalDateTime.now();
        LoggedUserHelper.getCurrentUserId().ifPresent(currentLoggedUser -> createdBy = currentLoggedUser);
    }

    @PreUpdate
    private void onModification() {
        updatedDate = LocalDateTime.now();
        LoggedUserHelper.getCurrentUserId().ifPresent(currentLoggedUser -> updatedBy = currentLoggedUser);
    }
}
