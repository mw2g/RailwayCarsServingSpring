package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.model.User;
import com.browarna.railwaycarsserving.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).get();

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String userName) {
//        Optional<User> userOptional = userRepository.findByUsername(userName);
//        User user = userOptional
//                .orElseThrow(() -> new UsernameNotFoundException("No user " +
//                        "Found with username : " + userName));
//
//        List<String> roleNames = Arrays.stream(Role.values())
//                .map(Role::name)
//                .collect(Collectors.toList());
//
//        List<GrantedAuthority> grantList = new ArrayList<>();
//        if (roleNames != null) {
//            for (String role : roleNames) {
//                // ROLE_USER, ROLE_ADMIN,..
//                GrantedAuthority authority = new SimpleGrantedAuthority(role);
//                grantList.add(authority);
//            }
//        }
//
//        return new org.springframework.security
//                .core.userdetails.User(user.getUsername(), user.getPassword(),
//                user.isEnabled(), true, true,
//                true, grantList);
//    }

//    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
//        return singletonList(new SimpleGrantedAuthority(role));
//    }

//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        AppUser appUser = this.appUserDAO.findUserAccount(userName);
//
//        if (appUser == null) {
//            System.out.println("User not found! " + userName);
//            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
//        }
//
//        System.out.println("Found User: " + appUser);
//
//        // [ROLE_USER, ROLE_ADMIN,..]
//        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
//
//        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//        if (roleNames != null) {
//            for (String role : roleNames) {
//                // ROLE_USER, ROLE_ADMIN,..
//                GrantedAuthority authority = new SimpleGrantedAuthority(role);
//                grantList.add(authority);
//            }
//        }
//
//        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(appUser.getUserName(), //
//                appUser.getPassword(), grantList);
//
//        return userDetails;
//    }
}
