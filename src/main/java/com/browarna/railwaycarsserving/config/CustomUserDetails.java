//package com.browarna.railwaycarsserving.config;
//
//import com.browarna.railwaycarsserving.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//
//    private String login;
//    private String password;
//    private Collection<? extends GrantedAuthority> grantedAuthorities;
//
//    public static CustomUserDetails fromUserToCustomUserDetails(User user) {
//        CustomUserDetails c = new CustomUserDetails();
//        c.login = user.getUsername();
//        c.password = user.getPassword();
//        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRoleEntity().getName()));
//        return c;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return grantedAuthorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return login;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
