package com.enershare.dto.user;

import com.enershare.dto.common.BaseDTO;
import com.enershare.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserDTO extends BaseDTO {

    private String email;

    private String password;

    private String repeatPassword;

    private String firstname;

    private String lastname;

    private Role role;

    private String connectorUrl;

    private String participantId;

    private String username;

    public UserDTO(Long id,String username, String firstname, String lastname, String email,String participantId, String connectorUrl, Role role) {
        this.setId(id);
        this.username =username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.participantId =participantId;
        this.connectorUrl = connectorUrl;
        this.role = role;


    }

}
