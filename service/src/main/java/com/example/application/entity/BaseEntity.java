package com.example.application.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    private boolean deleted = false;

    private String createdBy;

    @CreationTimestamp
    private ZonedDateTime createdDateTime;

    private String updatedBy;

    @UpdateTimestamp
    private ZonedDateTime updatedDateTime;
}
