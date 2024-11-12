package com.enershare.service.user;

import com.enershare.dto.response.SuccessResponse;
import com.enershare.dto.user.UserDTO;
import com.enershare.enums.Role;
import com.enershare.exception.EmailAlreadyExistsException;
import com.enershare.exception.EmailNotFoundException;
import com.enershare.mapper.UserMapper;
import com.enershare.model.user.User;
import com.enershare.repository.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String externalApiUrl;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${external.api.url}") String externalApiUrl
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.externalApiUrl = externalApiUrl;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public Page<UserDTO> getUsers(int page, int size) {
        return userRepository.getUsers(PageRequest.of(page, size));
    }

    public UserDTO getObject(Long id) {
        User user = findUserById(id);
        UserDTO dto = userMapper.map(user);
        dto.setPassword(null);
        return dto;
    }

    public void deleteObject(Long id) {
        User user = findUserById(id);
        userRepository.deleteById(user.getId());
    }

    public void createUser(UserDTO userDTO) {
        validateEmailUniqueness(userDTO.getEmail());
        User user = buildUser(userDTO);
        userRepository.save(user);
    }

    public void updateUser(UserDTO userDTO) {
        User existingUser = findUserById(userDTO.getId());
        updateUserDetails(existingUser, userDTO);
        userRepository.save(existingUser);
    }

    public ResponseEntity<SuccessResponse> registerUser(UserDTO userDTO) {
        userDTO.setRole(Role.USER);
        validateAndUpdateConnectorUrl(userDTO);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("User is already registered on Clearing House.");
        }

        Optional<User> dbUser = this.userRepository.findByEmail(userDTO.getEmail());
        if (dbUser.isPresent()) {
        }

        createUser(userDTO);
        SuccessResponse successResponse = new SuccessResponse("200", "User registered successfully.");

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }


    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }

    private User buildUser(UserDTO userDTO) {
        return User.builder()
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .email(userDTO.getEmail())
                .connectorUrl(userDTO.getConnectorUrl())
                .participantId(userDTO.getParticipantId())  // Add participant ID
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .build();
    }

    private void updateUserDetails(User existingUser, UserDTO userDTO) {
        if (!existingUser.getEmail().equals(userDTO.getEmail())) {
            validateEmailUniqueness(userDTO.getEmail());
        }

        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFirstname(userDTO.getFirstname());
        existingUser.setLastname(userDTO.getLastname());
        existingUser.setRole(userDTO.getRole());
        existingUser.setConnectorUrl(userDTO.getConnectorUrl());
        existingUser.setParticipantId(userDTO.getParticipantId());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
    }

    private void validateAndUpdateConnectorUrl(UserDTO userDTO) {
        ResponseEntity<String> response = restTemplate.getForEntity(externalApiUrl, String.class);
        String responseBody = response.getBody();

        if (responseBody == null || responseBody.isEmpty()) {
            throw new RuntimeException("Empty response from API");
        }

        Map<String, Object> responseMap = readJsonMap(responseBody);
        List<Map<String, Object>> components = getComponentsFromResponse(responseMap);

        boolean found = false;
        for (Map<String, Object> connector : components) {
            String owner = (String) connector.get("owner");
            String idsid = (String) connector.get("idsid");
            String participant = (String) connector.get("participant");

            if (userDTO.getEmail().equalsIgnoreCase(owner)) {
                userDTO.setConnectorUrl(idsid);
                userDTO.setParticipantId(participant);  // Set the participant ID
                found = true;
                break;
            }
        }

        if (!found) {
            throw new EmailNotFoundException("No matching connector URL found for the provided email");
        }
    }

    private Map<String, Object> readJsonMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON response: {}", e.getMessage(), e);
            throw new RuntimeException("Error processing JSON response", e);
        }
    }

    private List<Map<String, Object>> getComponentsFromResponse(Map<String, Object> responseMap) {
        List<Map<String, Object>> components = (List<Map<String, Object>>) responseMap.get("components");
        if (components == null) {
            throw new RuntimeException("Components field is missing in API response");
        }
        return components;
    }
}
