package com.enershare.service.user;

import com.enershare.dto.response.SuccessResponse;
import com.enershare.dto.user.UserDTO;
import com.enershare.enums.Role;
import com.enershare.exception.ParticipantAlreadyExistsException;
import com.enershare.exception.UsernameAlreadyExistsException;
import com.enershare.mapper.UserMapper;
import com.enershare.model.user.User;
import com.enershare.repository.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${external.api.url}") String externalApiUrl, JdbcTemplate jdbcTemplate
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.externalApiUrl = externalApiUrl;
        this.jdbcTemplate = jdbcTemplate;
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
        validateUsernameUniqueness(userDTO.getUsername());
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

        // Validate the selected participantId and connectorUrl
        validateParticipantAndConnector(userDTO.getParticipantId(), userDTO.getConnectorUrl());

        // Check username uniqueness
        validateUsernameUniqueness(userDTO.getUsername());

        // Save the new user
        createUser(userDTO);

        SuccessResponse successResponse = new SuccessResponse("200", "User registered successfully.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }


    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private void validateUsernameUniqueness(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
    }

    private User buildUser(UserDTO userDTO) {
        return User.builder()
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .email(userDTO.getEmail())
                .connectorUrl(userDTO.getConnectorUrl())
                .participantId(userDTO.getParticipantId())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .build();
    }

    private void updateUserDetails(User existingUser, UserDTO userDTO) {
        if (!existingUser.getUsername().equals(userDTO.getUsername())) {
            validateUsernameUniqueness(userDTO.getUsername());
        }

        existingUser.setUsername(userDTO.getUsername());
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

        if (components.isEmpty()) {
            throw new RuntimeException("No components found in API response");
        }

        // Use the first component to update the UserDTO
        Map<String, Object> firstConnector = components.get(0);
        String idsid = (String) firstConnector.get("idsid");
        String participant = (String) firstConnector.get("participant");

        userDTO.setConnectorUrl(idsid);
        userDTO.setParticipantId(participant); // Set the participant ID
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

    public List<Map<String, Object>> fetchRegisteredUsers() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Fetch the response as a Map to handle nested structure
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    externalApiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }
            );

            Map<String, Object> responseBody = response.getBody();

            // Extract "participants" if present
            if (responseBody != null && responseBody.containsKey("participants")) {
                Object participants = responseBody.get("participants");

                if (participants instanceof List) {
                    return (List<Map<String, Object>>) participants;
                }
            }
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP error fetching participants: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return List.of();
    }

    public List<String> getAllParticipantIds() {
        List<Map<String, Object>> allParticipants = fetchRegisteredUsers();

        return allParticipants.stream()
                .map(participant -> (String) participant.get("id"))
                .filter(Objects::nonNull) // Ensure IDs are not null
                .collect(Collectors.toList());
    }


    public List<String> getAvailableConnectors(String participantId) {
        return extractConnectorIds(fetchConnectorsForParticipant(participantId));
    }

    private List<Map<String, Object>> fetchConnectorsForParticipant(String participantId) {
        if (participantId == null || participantId.isBlank()) {
            log.warn("Participant ID is missing");
            throw new IllegalArgumentException("Participant ID cannot be null or empty");
        }

        try {
            // Fetch the API response
            ResponseEntity<String> response = restTemplate.getForEntity(externalApiUrl, String.class);
            String responseBody = response.getBody();

            if (responseBody == null || responseBody.isEmpty()) {
                throw new RuntimeException("Empty response from API");
            }

            Map<String, Object> responseMap = readJsonMap(responseBody);
            List<Map<String, Object>> components = getComponentsFromResponse(responseMap);

            // Filter connectors by the participantId
            List<Map<String, Object>> filteredConnectors = components.stream()
                    .filter(connector -> participantId.equals(connector.get("participant")))
                    .collect(Collectors.toList());

            // If no connectors found, log and return an empty list
            if (filteredConnectors.isEmpty()) {
                log.warn("No connectors found for participantId: {}", participantId);
            }

            return filteredConnectors;

        } catch (HttpClientErrorException.NotFound e) {
            log.warn("No connectors found for participant {}: {}", participantId, e.getMessage());
            return List.of(); // Return an empty list if not found
        } catch (Exception e) {
            log.error("Unexpected error fetching connectors for participant {}: {}", participantId, e.getMessage());
            throw new RuntimeException("Unexpected error while fetching connectors", e);
        }
    }

    // Extract the connector IDs from the response
    private List<String> extractConnectorIds(List<Map<String, Object>> connectors) {
        return connectors.stream()
                .map(connector -> (String) connector.get("idsid")) // Assuming 'idsid' is the connector ID
                .filter(id -> id != null)
                .collect(Collectors.toList());
    }

    private void validateParticipantAndConnector(String participantId, String connectorUrl) {
        if (userRepository.existsByParticipantIdAndConnectorUrl(participantId, connectorUrl)) {
            throw new ParticipantAlreadyExistsException("Participant ID for this connector URL already exists");
        }
    }


}