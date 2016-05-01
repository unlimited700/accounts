package com.apnavaidya.treasure.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Service;

import com.apnavaidya.treasure.accounts.model.User;


//@RestResource(exported = false)
public interface UserDao extends CrudRepository<User, Long> {

	User findByEmail(String email);

}