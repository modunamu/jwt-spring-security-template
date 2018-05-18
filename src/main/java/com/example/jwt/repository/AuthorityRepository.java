package com.example.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.jwt.model.Authority;
import com.example.jwt.model.AuthorityName;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

	Authority findByAuthorityName(AuthorityName authorityName);

}
