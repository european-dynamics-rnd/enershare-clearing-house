package com.enershare.dto.user;

import com.enershare.dto.common.BaseDTO;
import com.enershare.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserDTO extends BaseDTO {
    @NotNull
    private String username;

    @NotNull
    private String email;
    @JsonIgnore
    @NotNull
    private String password;
    @JsonIgnore
    @NotNull
    private String repeatPassword;

    private String firstName;

    private String lastName;

    private Role role;

    public UserDTO(Long id, String firstName, String lastName, String email, Role role) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;

    }

}
