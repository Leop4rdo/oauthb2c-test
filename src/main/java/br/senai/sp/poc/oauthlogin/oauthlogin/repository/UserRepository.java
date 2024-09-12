package br.senai.sp.poc.oauthlogin.oauthlogin.repository;

import br.senai.sp.poc.oauthlogin.oauthlogin.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
