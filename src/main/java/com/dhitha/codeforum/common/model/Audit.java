package com.dhitha.codeforum.common.model;

import com.dhitha.codeforum.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt", "createdBy", "createdAt"},
    allowGetters = true)
public abstract class Audit implements Serializable {
  @Column(nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  @Column @LastModifiedDate private LocalDateTime updatedAt;

  @Column(nullable = false, updatable = false)
  @CreatedBy
  private User createdBy;

  @Column @LastModifiedBy private User updatedBy;
}
