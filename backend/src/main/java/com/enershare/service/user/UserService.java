//package com.enershare.service.user;
//
//import com.enershare.dto.user.ChangePasswordRequest;
//import com.enershare.dto.user.JwtAuthenticationResponse;
//import com.enershare.dto.user.UserDTO;
//import com.enershare.model.LocalUser;
//import com.enershare.model.user.User;
//import com.enershare.repository.user.UserRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import javax.validation.constraints.NotBlank;
//
//@Service
//@Slf4j
//public class UserService {
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final JWTService jwtService;
//    private final RoleRepository roleRepository;
//    private final AuthenticationManager authenticationManager;
//    private final TokenProvider tokenProvider;
//    private final EntityManager entityManager;
//    private final PlatformTransactionManager transactionManager;
//    private TransactionTemplate transactionTemplate;
//
////    @Transactional
////    public UserDTO changePassword(ChangePasswordRequest optionalChangePasswordRequest) {
////
////        ChangePasswordRequest changePasswordRequest = Optional.ofNullable(optionalChangePasswordRequest)
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error!"));
////
////        User currentUser = userRepository.findByUsername(changePasswordRequest.getUsername())
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error!"));
////
////
////        if (StringUtils.isNotBlank(changePasswordRequest.getPassword()) || StringUtils.isNotBlank(changePasswordRequest.getRepeatPassword())) {
////            if (!changePasswordRequest.getPassword().equals(changePasswordRequest.getRepeatPassword())) {
////                throw new ChangePasswordException();
////            } else {
////                currentUser.setEnabled(true);
////                currentUser.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
////            }
////        }
////
////        UserDTO responseUserDTO = userMapper.mapUserToDto(currentUser);
////        return responseUserDTO;
////    }
//
//
//    @Transactional
//    public ResponseEntity<?> authenticate(@NotBlank String username, @NotBlank String enteredPassword) {
//
//        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
//
//        if (passwordEncoder.matches(enteredPassword, user.getPassword())) {
//            user.setEnabled(true);
//            UserDetails userDetails =
//                    new LocalUser(user.getEmail(), user.getPassword(),
//                            true, true, true,
//                            true,
//                            GeneralUtils.buildSimpleGrantedAuthorities(user.getRolesSet()), user, user.getRoles());
//
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, enteredPassword)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = tokenProvider.createToken(authentication);
//            LocalUser localUser = (LocalUser) authentication.getPrincipal();
//
//            UserDTO userDTO = this.userMapper.mapUserToDtoWithMenu(localUser.getUser());
//            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userDTO));
//        } else {
//
//            throw new IncorrectPasswordException();
//        }
//    }
//}
