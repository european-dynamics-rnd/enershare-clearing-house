package com.enershare.service.user;

import com.enershare.dto.user.UserDTO;
import com.enershare.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Page<UserDTO> getUsers(int page, int size) {
        Page<UserDTO> users = userRepository.getUsers(PageRequest.of(page, size));
        return users;
    }

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

//    @Transactional
//    public UserDTO changePassword(ChangePasswordRequest optionalChangePasswordRequest) {
//
//        ChangePasswordRequest changePasswordRequest = Optional.ofNullable(optionalChangePasswordRequest)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error!"));
//
//        User currentUser = userRepository.findByUsername(changePasswordRequest.getUsername())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error!"));
//
//
//        if (StringUtils.isNotBlank(changePasswordRequest.getPassword()) || StringUtils.isNotBlank(changePasswordRequest.getRepeatPassword())) {
//            if (!changePasswordRequest.getPassword().equals(changePasswordRequest.getRepeatPassword())) {
//                throw new ChangePasswordException();
//            } else {
//                currentUser.setEnabled(true);
//                currentUser.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
//            }
//        }
//
//        UserDTO responseUserDTO = userMapper.mapUserToDto(currentUser);
//        return responseUserDTO;
//    }


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
}
