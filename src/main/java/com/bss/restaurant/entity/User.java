package com.bss.restaurant.entity;

import com.bss.restaurant.dto.internal.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "image")
    @NotNull
    private String image;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "father_name")
    @NotNull
    private String fatherName;

    @Column(name = "mother_name")
    @NotNull
    private String motherName;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "phone_number")
    @NotNull
    private String phoneNumber;

    @Column(name = "gender_id")
    @NotNull
    private Integer genderId;

    @Column(name = "dob")
    @NotNull
    private LocalDate dob;

    @Column(name = "nid")
    @NotNull
    private String nid;

    @Column(name = "force_change_password")
    private boolean forceChangePassword;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "provider")
    private String provider;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private RefreshToken refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
