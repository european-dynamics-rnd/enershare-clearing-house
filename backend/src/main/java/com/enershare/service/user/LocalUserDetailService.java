//package com.enershare.service.user;
//
//import com.enershare.model.user.User;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service("localUserDetailService")
//public class LocalUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    @Transactional
//    public LocalUser loadUserByUsername(final String username) throws UsernameNotFoundException {
//        User user = userService.findUserByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User " + username + " was not found in the database");
//        }
//        return createLocalUser(user);
//    }
//
//
//    public LocalUser createLocalUser(User user) {
//        return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
//                true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRolesSet()), user, user.getRoles());
//    }
//}
