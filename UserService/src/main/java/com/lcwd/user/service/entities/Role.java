package com.lcwd.user.service.entities;

import java.util.HashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "roles_table")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(length = 20, name = "role_name")
    private String roleName;

      @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonBackReference
    private Set<User> users = new HashSet<>();


}
