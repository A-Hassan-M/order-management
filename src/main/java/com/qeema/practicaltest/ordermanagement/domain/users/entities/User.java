package com.qeema.practicaltest.ordermanagement.domain.users.entities;

import lombok.Data;
import java.util.Set;
import java.util.Collection;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@Entity
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @Basic(optional = false)
    @GenericGenerator(name = "generator")
    @GeneratedValue(generator = "generator")
    private Long id;
    @Column(name = "_name")
    private String name;
    private String email;
    private String password;
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_code", referencedColumnName = "code")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

