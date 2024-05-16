package com.enershare.config;

import com.enershare.dto.user.UserDTO;
import com.enershare.enums.Role;
import com.enershare.repository.user.UserRepository;
import com.enershare.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements ApplicationRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if the admin user exists
        if (!userRepository.existsByEmail("admin")) {
            // Create the admin user
            UserDTO adminUserDTO = new UserDTO();
            adminUserDTO.setFirstname("admin");
            adminUserDTO.setLastname("admin");
            adminUserDTO.setEmail("admin");
            adminUserDTO.setConnectorUrl("admin");
            adminUserDTO.setPassword("admin");
            adminUserDTO.setRole(Role.ADMIN);
            userService.createUser(adminUserDTO);
            // You may want to log a message indicating that the admin user has been created
        }
    }
}
