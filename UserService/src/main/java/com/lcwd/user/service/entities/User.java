package com.lcwd.user.service.entities;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "micro_users")
public class User {

    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "NAME", length = 20)
    @NotBlank
    private String name;

    @Column(name = "EMAIL")
    @NotBlank
    private String email;
    @Column(name = "ABOUT")
    private String about;
    
    @NotBlank
    private String password;
    //other user properties that you want

    @Transient
    private List<Rating> ratings=new ArrayList<>();
    
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonBackReference
    private Role role;


}
