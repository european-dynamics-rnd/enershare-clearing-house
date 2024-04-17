package com.enershare.dto.user;

public record JwtAuthenticationResponse(String accessToken, UserDTO userDTO) {
}
