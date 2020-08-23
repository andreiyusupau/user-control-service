package com.nevermind.usercontrolservice.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name="users")
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message="Username can't be empty")
    @Size(min=3, max=16,message="Username length must be between 3 and 16")
    @Column(unique = true)
    @Pattern(regexp = "[a-zA-Z]+",message="Username may contain latin letters only")
    private String username;
    @NotBlank(message="Password can't be empty")
    @Size(min=3, max=16,message="Password length must be between 3 and 16")
    @Pattern(regexp ="([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*",message="Password should contain minimum 1 letter and 1 digit, use only latin letters")
    private String password;
    @Transient
    @NotBlank
    private String confirmPassword;
    @NotBlank(message="First name can't be empty")
    @Size(min=1, max=16,message="First name length must be between 3 and 16")
    @Pattern(regexp = "[a-zA-Z]+",message="First name may contain latin letters only")
    private String firstName;
    @NotBlank(message="Last name can't be empty")
    @Size(min=1, max=16,message="Last name length must be between 3 and 16")
    @Pattern(regexp = "[a-zA-Z]+",message="Last name may contain latin letters only")
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate createdAt;

    public UserAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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
        return status.equals(Status.ACTIVE);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
