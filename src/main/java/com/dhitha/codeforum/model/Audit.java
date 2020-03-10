package com.dhitha.codeforum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt", "createdBy", "createdAt"},
    allowGetters = true
)
public abstract class Audit implements Serializable {
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "createdAt", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updatedAt")
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Column(name="createdBy", nullable = false, updatable = false)
  @CreatedBy
  private User createdBy;

  @Column(name="createdBy")
  @LastModifiedBy
  private User updatedBy;


}
