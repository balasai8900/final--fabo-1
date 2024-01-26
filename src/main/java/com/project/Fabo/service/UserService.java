package com.project.Fabo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.Fabo.entity.User;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	public String getUserStoreCodeByEmail(String userEmail);

}