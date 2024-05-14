package com.enershare.mapper;

import com.enershare.dto.user.UserDTO;
import com.enershare.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;


@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    UserDTO map(User user);

    void updateUserDTO(User user, @MappingTarget UserDTO dto);

    void updateUser(UserDTO dto, @MappingTarget User user);


}
