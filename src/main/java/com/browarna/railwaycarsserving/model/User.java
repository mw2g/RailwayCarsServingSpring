package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    private String username;
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany( mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
                )
    private List<RefreshToken> refreshTokens;

    @Email
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String initials;
    private Instant created;
    private boolean enabled;

    public void add(RefreshToken tempRefreshToken) {
        if (refreshTokens == null) {
            refreshTokens = new ArrayList<>();
        }
        refreshTokens.add(tempRefreshToken);
        tempRefreshToken.setUser(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
