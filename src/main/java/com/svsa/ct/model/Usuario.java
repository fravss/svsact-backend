package com.svsa.ct.model;

import com.svsa.ct.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome;

    @Column(unique=true)
    private String email;
    private String senha;

    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == Role.COORDENADORES) return List.of(new SimpleGrantedAuthority("COORDENADOR"));
        else return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;// UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true; //UserDetails.super.isEnabled();
    }
}
