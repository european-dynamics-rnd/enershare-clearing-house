package com.enershare.dto.user;

import com.enershare.dto.common.BaseDTO;
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

    @NotNull
    private String password;

    @NotNull
    private String repeatPassword;

    private String firstName;

    private String lastName;

    public UserDTO(Long id,String firstName,String lastName, String email) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

}
