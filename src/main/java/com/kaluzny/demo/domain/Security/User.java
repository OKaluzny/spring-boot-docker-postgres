package com.kaluzny.demo.domain.Security;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String password;
    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Authority> authority;
}
