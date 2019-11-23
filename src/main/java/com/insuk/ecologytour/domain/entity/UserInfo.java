package com.insuk.ecologytour.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    final static public UserInfo EMPTY = new UserInfo();

    @Id
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(nullable = false)
    private String password;
}
