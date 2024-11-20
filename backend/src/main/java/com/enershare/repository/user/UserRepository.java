package com.enershare.repository.user;

import com.enershare.dto.user.UserDTO;
import com.enershare.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT new com.enershare.dto.user.UserDTO(u.id,u.username ,u.firstname, u.lastname, u.email,u.participantId,u.connectorUrl,u.role) FROM User u ")
    Page<UserDTO> getUsers(Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.participantId = :participantId AND u.connectorUrl = :connectorUrl")
    boolean existsByParticipantIdAndConnectorUrl(String participantId, String connectorUrl);
}
