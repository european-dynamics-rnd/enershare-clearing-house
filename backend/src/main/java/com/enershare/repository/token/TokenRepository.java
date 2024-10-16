package com.enershare.repository.token;

import com.enershare.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t inner join User u on t.userId = u.id where u.id =:userId and (t.expired =false or t.revoked=false) ")
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);

    void deleteByExpirationDateLessThan(Date now);
}
