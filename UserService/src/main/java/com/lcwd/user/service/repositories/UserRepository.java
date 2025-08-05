package com.lcwd.user.service.repositories;

import com.lcwd.user.service.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String>
{
	Optional<User> findByEmail(String email);
}
