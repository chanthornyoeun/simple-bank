package com.example.bank.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditMetadata {
    @CreatedDate
    private Date createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Date modifiedDate;
    @LastModifiedBy
    private String modifiedBy;
    @Version
    private Long version;
}
