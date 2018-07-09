package com.example.timetracking.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);

}