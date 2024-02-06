package com.shahrokhi.springbootv3jwt.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_token")
public class Token extends BaseEntity {
  @Column(unique = true)
  public String token;
  public boolean revoked;
  public boolean expired;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;
}
