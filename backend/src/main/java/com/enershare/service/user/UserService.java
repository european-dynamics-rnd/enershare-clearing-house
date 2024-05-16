package com.enershare.service.user;

import com.enershare.dto.user.UserDTO;
import com.enershare.exception.EmailAlreadyExistsException;
import com.enershare.mapper.UserMapper;
import com.enershare.model.user.User;
import com.enershare.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
@Slf4j
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public long getTotalUsers() {
        return userRepository.count();
    }

    public Page<UserDTO> getUsers(int page, int size) {
        Page<UserDTO> users = userRepository.getUsers(PageRequest.of(page, size));
        return users;
    }

    public UserDTO getObject(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();

        UserDTO dto = userMapper.map(user);
        dto.setPassword(null); // Fix the typo here
        return dto;
    }

//    public void createOrUpdateUser(UserDTO dto) {
//
//        if (dto.getId() != null) {
//            User user = userRepository.findById(dto.getId()).orElseThrow();
//            userMapper.updateUser(dto, user);
//        } else {
//            var user = User
//                    .builder()
//                    .firstname(dto.getFirstname())
//                    .lastname(dto.getLastname())
//                    .email(dto.getEmail())
//                    .connectorUrl(dto.getConnectorUrl())
//                    .password(passwordEncoder.encode(dto.getPassword()))
//                    .role(dto.getRole())
//                    .build();
//            userRepository.save(user);
//        }
//    }

    public void deleteObject(Long id) {
        User optionalEntity = userRepository.findById(id)
                .orElseThrow();

        userRepository.deleteById(optionalEntity.getId());
    }

    public void createUser(@RequestBody UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = User.builder()
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .email(userDTO.getEmail())
                .connectorUrl(userDTO.getConnectorUrl())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .build();

        userRepository.save(user);
    }

    public void updateUser(@RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
        User existingUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("User not found"));

        String newEmail = userDTO.getEmail();
        if (!existingUser.getEmail().equals(newEmail)) {
            // Check if the new email already exists in the database
            Optional<User> userWithNewEmail = userRepository.findByEmail(newEmail);
            if (userWithNewEmail.isPresent() && !userWithNewEmail.get().equals(existingUser)) {
                // Email already exists and belongs to another user
                throw new IllegalArgumentException("Email already exists for another user");
            }
        }

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFirstname(userDTO.getFirstname());
        existingUser.setLastname(userDTO.getLastname());
        existingUser.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
            existingUser.setPassword(hashedPassword);
        }

        // Save the updated user
        userRepository.save(existingUser);
    }

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

