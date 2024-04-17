package com.enershare.dto.auth;

import com.enershare.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * JWT placeholder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JWTResponseDTO {
    private String jwt;
    private UserDTO user;
}
